/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.binding

import com.valaphee.foundry.databind.ObservableValueChanged
import com.valaphee.foundry.databind.property.toInternalProperty
import com.valaphee.foundry.databind.value.ObservableValue

/**
 * @author Kevin Ludwig
 */
@Suppress("UNCHECKED_CAST")
class ComputedBinding<S : Any, T : Any>(
    source: ObservableValue<S>,
    convert: (S) -> T
) : BaseBinding<S, T>(source, convert(source.value).toInternalProperty(), mutableListOf()) {
    init {
        subscriptions.add(source.onChange {
            val oldValue = convert(it.oldValue)
            val newValue = convert(it.newValue)
            if (target.updateWithEvent(oldValue, newValue, it)) onChange.publish(ObservableValueChanged(oldValue, newValue, this, listOf(it) + it.trace))
        })
    }
}
