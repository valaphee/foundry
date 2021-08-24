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

package com.valaphee.foundry.databind.value

import com.valaphee.foundry.databind.ObservableValueChanged
import com.valaphee.foundry.databind.toAtom
import com.valaphee.foundry.event.subscribeWithoutResult
import com.valaphee.foundry.event.unscopedEventBus

/**
 * @author Kevin Ludwig
 */
class CompositeObservableValue<T : Any>(
    initialValue: T,
    sources: Iterable<ValueWithConverter<Any, T>>
) : ObservableValue<T> {
    private val atom = initialValue.toAtom()

    override val value get() = atom.get()

    private val onChange = unscopedEventBus()

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
