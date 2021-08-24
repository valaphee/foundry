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
