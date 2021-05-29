/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.value

import com.valaphee.foundry.databind.ObservableValueChanged
import com.valaphee.foundry.databind.toAtom
import com.valaphee.foundry.event.UnscopedEventBus
import com.valaphee.foundry.event.subscribeWithoutResult

/**
 * @author Kevin Ludwig
 */
class CompositeObservableValue<T : Any>(
    initialValue: T,
    sources: Iterable<ValueWithConverter<Any, T>>
) : ObservableValue<T> {
    private val atom = initialValue.toAtom()

    override val value get() = atom.get()

    private val onChange = UnscopedEventBus.create()

    init {
        sources.forEach { (value, convert) ->
            value.onChange { event ->
                val (_, newValue) = event
                atom.transform {
                    val convertedValue = convert(newValue)
                    onChange.publish(ObservableValueChanged(it, newValue, this, this, listOf(event) + event.trace))
                    convertedValue
                }
            }
        }
    }

    override fun onChange(callback: (ObservableValueChanged<T>) -> Unit) = onChange.subscribeWithoutResult<ObservableValueChanged<T>> { callback(it) }
}
