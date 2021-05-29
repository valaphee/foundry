/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.binding

import com.valaphee.foundry.databind.ObservableValueChanged
import com.valaphee.foundry.databind.ScalarChange
import com.valaphee.foundry.databind.converter.IsomorphicConverter
import com.valaphee.foundry.databind.property.InternalProperty

/**
 * @author Kevin Ludwig
 */
class BidirectionalBinding<S : Any, T : Any>(
    source: InternalProperty<S>,
    target: InternalProperty<T>,
    convert: IsomorphicConverter<S, T>
) : BaseBinding<S, T>(source, target, mutableListOf()) {
    private val reverseConvert = convert.reverseConverter()

    init {
        subscriptions.add(source.onChange { runWithDisposeOnFailure { if (target.updateWithEvent(convert(it.oldValue), convert(it.newValue), it)) onChange.publish(ObservableValueChanged(it.oldValue, it.newValue, this, this, listOf(it) + it.trace)) } })
        subscriptions.add(target.onChange {
            runWithDisposeOnFailure {
                val oldValue = reverseConvert(it.oldValue)
                val newValue = reverseConvert(it.newValue)
                if (source.updateWithEvent(oldValue, newValue, it)) onChange.publish(ObservableValueChanged(oldValue, newValue, this, this, listOf(it) + it.trace, ScalarChange))
            }
        })
    }
}
