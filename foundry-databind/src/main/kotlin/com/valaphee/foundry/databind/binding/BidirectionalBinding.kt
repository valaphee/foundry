/*
 * MIT License
 *
 * Copyright (c) 2021, Valaphee.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
