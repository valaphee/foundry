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
import com.valaphee.foundry.event.Subscription
import com.valaphee.foundry.event.dispose
import com.valaphee.foundry.event.subscribeToWithoutResult
import com.valaphee.foundry.event.unscopedEventBus
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
    private val onChange = unscopedEventBus()

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
