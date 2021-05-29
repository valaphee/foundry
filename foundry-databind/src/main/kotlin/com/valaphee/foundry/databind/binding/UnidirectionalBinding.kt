/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.binding

import com.valaphee.foundry.databind.ObservableValueChanged
import com.valaphee.foundry.databind.converter.Converter
import com.valaphee.foundry.databind.property.InternalProperty
import com.valaphee.foundry.databind.value.ObservableValue

/**
 * @author Kevin Ludwig
 */
class UnidirectionalBinding<S : Any, T : Any>(
    source: ObservableValue<S>,
    target: InternalProperty<T>,
    convert: Converter<S, T>
) : BaseBinding<S, T>(source, target, mutableListOf()) {
    init {
        subscriptions.add(source.onChange {
            runWithDisposeOnFailure {
                val oldValue = convert(it.oldValue)
                val newValue = convert(it.newValue)
                if (target.updateWithEvent(oldValue, newValue, it)) onChange.publish(ObservableValueChanged(oldValue, newValue, this, this, listOf(it) + it.trace))
            }
        })
    }
}
