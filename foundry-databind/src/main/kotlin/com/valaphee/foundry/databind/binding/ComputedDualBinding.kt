/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.binding

import com.valaphee.foundry.databind.ObservableValueChanged
import com.valaphee.foundry.databind.property.toInternalProperty
import com.valaphee.foundry.databind.value.ObservableValue
import com.valaphee.foundry.event.Subscription
import com.valaphee.foundry.event.UnscopedEventBus
import com.valaphee.foundry.event.dispose
import com.valaphee.foundry.event.subscribeToWithoutResult
import com.valaphee.foundry.util.DisposeState
import com.valaphee.foundry.util.NotDisposed

/**
 * @author Kevin Ludwig
 */
class ComputedDualBinding<out S0 : Any, out S1 : Any, T : Any>(
    private val source0: ObservableValue<S0>,
    private val source1: ObservableValue<S1>,
    private val compute: (S0, S1) -> T
) : Binding<T> {
    private val target = compute(source0.value, source1.value).toInternalProperty()

    override val value: T
        get() {
            check(disposed.not()) { "Can't calculate the value of a binding which is disposed" }
            return target.value
        }

    override var disposeState: DisposeState = NotDisposed
        internal set

    private val subscriptions = mutableListOf<Subscription>()
    private val onChange = UnscopedEventBus.create()

    init {
        subscriptions.add(source0.onChange {
            val oldValue = compute(it.oldValue, source1.value)
            val newValue = compute(it.newValue, source1.value)
            if (oldValue != newValue) {
                target.value = newValue
                onChange.publish(ObservableValueChanged(oldValue, newValue, this, this, listOf(it) + it.trace))
            }
        })
        subscriptions.add(source1.onChange {
            val oldValue = compute(source0.value, it.oldValue)
            val newValue = compute(source0.value, it.newValue)
            if (oldValue != newValue) {
                target.value = newValue
                onChange.publish(ObservableValueChanged(oldValue, newValue, this, this, listOf(it) + it.trace))
            }
        })
    }

    override fun dispose(disposeState: DisposeState) {
        this.disposeState = disposeState
        onChange.cancel()
        subscriptions.dispose()
    }

    override fun onChange(callback: (ObservableValueChanged<T>) -> Unit) = onChange.subscribeToWithoutResult<ObservableValueChanged<T>> { callback(it) }
}
