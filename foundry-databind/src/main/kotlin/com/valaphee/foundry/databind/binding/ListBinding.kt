/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

@file:JvmName("ListBindings")

package com.valaphee.foundry.databind.binding

import com.valaphee.foundry.databind.ListAdd
import com.valaphee.foundry.databind.ListAddAll
import com.valaphee.foundry.databind.ListAddAllAt
import com.valaphee.foundry.databind.ListAddAt
import com.valaphee.foundry.databind.ListChange
import com.valaphee.foundry.databind.ListClear
import com.valaphee.foundry.databind.ListRemove
import com.valaphee.foundry.databind.ListRemoveAll
import com.valaphee.foundry.databind.ListRemoveAllWhen
import com.valaphee.foundry.databind.ListRemoveAt
import com.valaphee.foundry.databind.ListSet
import com.valaphee.foundry.databind.ObservableValueChanged
import com.valaphee.foundry.databind.ScalarChange
import com.valaphee.foundry.databind.collection.ObservableList
import com.valaphee.foundry.databind.property.toInternalProperty
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

/**
 * @author Kevin Ludwig
 */
@Suppress("UNCHECKED_CAST")
class ListBinding<S : Any, T : Any>(
    source: ObservableList<S>,
    convert: (S) -> T
) : BaseBinding<PersistentList<S>, PersistentList<T>>(source, source.value.map { convert(it) }.toInternalProperty(), mutableListOf()) {
    init {
        subscriptions.add(source.onChange { event ->
            if (event.trace.any { it.emitter == this }) throw CircularBindingException("Circular binding detected with trace ${event.trace.joinToString()} for property $this")
            val type = event.type
            val oldValue = target.value
            val maybeNewValue = target.transformValue {
                when (type) {
                    is ScalarChange -> event.newValue.map { convert(it) }
                    is ListChange -> {
                        when (type) {
                            is ListAdd<*> -> oldValue.add(convert(type.element as S))
                            is ListAddAt<*> -> oldValue.add(type.index, convert(type.element as S))
                            is ListRemove<*> -> oldValue.remove(convert(type.element as S))
                            is ListRemoveAt -> oldValue.removeAt(type.index)
                            is ListSet<*> -> oldValue.set(type.index, convert(type.element as S))
                            is ListAddAll<*> -> oldValue.addAll(type.elements.map { convert(it as S) })
                            is ListAddAllAt<*> -> oldValue.addAll(type.index, type.elements.map { convert(it as S) })
                            is ListRemoveAll<*> -> oldValue.removeAll(type.elements.map { convert(it as S) })
                            is ListRemoveAllWhen<*> -> oldValue.removeAll { (type.predicate as (T) -> Boolean)(it) }
                            ListClear -> oldValue.clear()
                        }
                    }
                }
            }
            if (maybeNewValue.successful && oldValue != maybeNewValue.value) onChange.publish(ObservableValueChanged(oldValue, maybeNewValue.value, this, this, listOf(event) + event.trace, event.type))
        })
    }
}

private inline fun <T, R> PersistentList<T>.map(transform: (T) -> R): PersistentList<R> {
    var result = persistentListOf<R>()
    forEach { result = result.add(transform(it)) }
    return result
}
