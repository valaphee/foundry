/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.property

import com.valaphee.foundry.databind.ChangeType
import com.valaphee.foundry.databind.Maybe
import com.valaphee.foundry.databind.ObservableValueChanged
import com.valaphee.foundry.databind.ScalarChange
import com.valaphee.foundry.databind.binding.BidirectionalBinding
import com.valaphee.foundry.databind.binding.Binding
import com.valaphee.foundry.databind.binding.CircularBindingException
import com.valaphee.foundry.databind.binding.UnidirectionalBinding
import com.valaphee.foundry.databind.collection.DefaultListProperty
import com.valaphee.foundry.databind.collection.DefaultMapProperty
import com.valaphee.foundry.databind.collection.DefaultSetProperty
import com.valaphee.foundry.databind.collection.ListProperty
import com.valaphee.foundry.databind.collection.MapProperty
import com.valaphee.foundry.databind.collection.SetProperty
import com.valaphee.foundry.databind.converter.IdentityConverter
import com.valaphee.foundry.databind.converter.IsomorphicConverter
import com.valaphee.foundry.databind.converter.toConverter
import com.valaphee.foundry.databind.toAtom
import com.valaphee.foundry.databind.value.ObservableValue
import com.valaphee.foundry.databind.value.ValueValidationFailed
import com.valaphee.foundry.databind.value.ValueValidationFailedException
import com.valaphee.foundry.databind.value.ValueValidationSuccessful
import com.valaphee.foundry.databind.value.WritableValue
import com.valaphee.foundry.event.Subscription
import com.valaphee.foundry.event.UnscopedEventBus
import com.valaphee.foundry.event.subscribeWithoutResult
import com.valaphee.foundry.util.Predicate
import org.apache.logging.log4j.LogManager

/**
 * @author Kevin Ludwig
 */
interface Property<T : Any> : WritableValue<T>, ObservableValue<T> {
    fun bind(other: Property<T>, updateWhenBound: Boolean = true): Binding<T>

    fun <S : Any> bind(other: Property<S>, updateWhenBound: Boolean = true, converter: IsomorphicConverter<S, T>): Binding<T>

    fun asDelegate(): PropertyDelegate<T> = DefaultPropertyDelegate(this)
}

fun <T : Any> T.toProperty(validate: (T) -> Boolean = { true }): Property<T> = DefaultProperty(this, validate)

fun <T : Any> Iterable<T>.toProperty(validate: Predicate<Iterable<T>> = { true }): ListProperty<T> = DefaultListProperty(this.toList(), validate)

fun <T : Any> Collection<T>.toProperty(validate: Predicate<Collection<T>> = { true }): ListProperty<T> = DefaultListProperty(this.toList(), validate)

fun <T : Any> List<T>.toProperty(validate: Predicate<List<T>> = { true }): ListProperty<T> = DefaultListProperty(this, validate)

fun <T : Any> Set<T>.toProperty(validate: Predicate<Set<T>> = { true }): SetProperty<T> = DefaultSetProperty(this, validate)

fun <K : Any, V : Any> Map<K, V>.toProperty(validate: Predicate<Map<K, V>> = { true }): MapProperty<K, V> = DefaultMapProperty(this, validate)

internal fun <T : Any> T.toInternalProperty(validate: (T) -> Boolean = { true }): InternalProperty<T> = DefaultProperty(this, validate)

/**
 * @author Kevin Ludwig
 */
interface InternalProperty<T : Any> : Property<T> {
    fun updateWithEvent(oldValue: T, newValue: T, event: ObservableValueChanged<Any>): Boolean
}

/**
 * @author Kevin Ludwig
 */
open class DefaultProperty<T : Any>(
    initialValue: T,
    private val validate: Predicate<T> = { true }
) : InternalProperty<T> {
    override var value
        get() = atom.get()
        set(value) {
            updateCurrentValue { value }
        }

    private val atom = initialValue.toAtom()
    private val identityConverter = IdentityConverter<T>()

    private val onChange = UnscopedEventBus.create()

    override fun updateValue(newValue: T) = transformValue { newValue }

    @Suppress("UNCHECKED_CAST")
    override fun transformValue(transform: (oldValue: T) -> T) = try {
        ValueValidationSuccessful(updateCurrentValue(transform = transform))
    } catch (ex: ValueValidationFailedException) {
        ValueValidationFailed(ex.newValue as T, ex)
    }

    override fun onChange(callback: (ObservableValueChanged<T>) -> Unit): Subscription {
        /*log.trace("Subscribing to changes to property $this")*/
        return onChange.subscribeWithoutResult<ObservableValueChanged<T>>() { callback(it) }
    }

    override fun bind(other: Property<T>, updateWhenBound: Boolean) = bind(other, updateWhenBound, identityConverter)

    override fun <S : Any> bind(other: Property<S>, updateWhenBound: Boolean, converter: IsomorphicConverter<S, T>): Binding<T> {
        /*log.trace("Binding property $this to other property $other")*/
        checkSelfBinding(other)
        check(other is InternalProperty<S>) { "Can only bind Properties which implement InternalProperty" }
        if (updateWhenBound) updateCurrentValue { converter(other.value) }
        return BidirectionalBinding(other, this, converter)
    }

    override fun updateFrom(observable: ObservableValue<T>, updateWhenBound: Boolean) = updateFrom(observable, updateWhenBound) { it }

    override fun <S : Any> updateFrom(observable: ObservableValue<S>, updateWhenBound: Boolean, convert: (S) -> T): Binding<T> {
        /*log.trace("Starting to update property $this from $observable")*/
        checkSelfBinding(observable)
        if (updateWhenBound) updateCurrentValue { convert(observable.value) }
        return UnidirectionalBinding(observable, this, convert.toConverter())
    }

    override fun updateWithEvent(oldValue: T, newValue: T, event: ObservableValueChanged<Any>): Boolean {
        /*log.trace("Trying to update $this using event $event with new value $newValue")*/
        return try {
            if (event.trace.any { it.emitter == this }) throw CircularBindingException("Circular binding detected with trace ${event.trace.joinToString()} for property $this") else {
                var changed = false
                var eventToSend = Maybe.empty<ObservableValueChanged<T>>()
                atom.transform {
                    if (validate(newValue).not()) throw ValueValidationFailedException(newValue, "The given value $newValue is invalid")
                    if (oldValue != newValue) {
                        changed = true
                        eventToSend = Maybe.of(ObservableValueChanged(oldValue, newValue, this, this, listOf(event) + event.trace))
                    }
                    newValue
                }
                eventToSend.map {
                    /*log.trace("Old value $oldValue of $this differs from new value $newValue, firing change event")*/
                    onChange.publish(it)
                }
                changed
            }
        } catch (ex: CircularBindingException) {
            log.warn("Bound Property was not updated due to circular dependency", ex)
            false
        }
    }

    @Synchronized
    protected fun updateCurrentValue(type: ChangeType = ScalarChange, transform: (T) -> T): T {
        var eventToSend = Maybe.empty<ObservableValueChanged<T>>()
        atom.transform {
            val newValue = transform(it)
            if (validate(newValue).not()) throw ValueValidationFailedException(newValue, "The given value $newValue is invalid")
            if (it != newValue) eventToSend = Maybe.of(ObservableValueChanged(it, newValue, this, this, listOf(), type))
            newValue
        }
        eventToSend.map {
            /*log.trace("Old value ${it.oldValue} of $this differs from new value ${it.newValue}, firing change event")*/
            onChange.publish(it)
        }
        return atom.get()
    }

    private fun checkSelfBinding(other: ObservableValue<Any>) {
        require(this !== other) { "Can't bind a property to itself" }
    }
}

private val log = LogManager.getLogger(Property::class.java)
