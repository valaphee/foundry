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
import com.valaphee.foundry.databind.property.InternalProperty
import com.valaphee.foundry.databind.value.ObservableValue
import com.valaphee.foundry.event.Subscriptions
import com.valaphee.foundry.event.dispose
import com.valaphee.foundry.event.subscribeWithoutResult
import com.valaphee.foundry.event.unscopedEventBus
import com.valaphee.foundry.util.DisposeState
import com.valaphee.foundry.util.NotDisposed

/**
 * @author Kevin Ludwig
 */
abstract class BaseBinding<S : Any, T : Any>(
    internal val source: ObservableValue<S>,
    internal val target: InternalProperty<T>,
    internal val subscriptions: Subscriptions
) : Binding<T> {
    override val value: T
        get() {
            check(disposed.not()) { "Can't calculate the value of a binding which is disposed" }
            return target.value
        }

    override var disposeState: DisposeState = NotDisposed
        internal set

    protected val onChange = unscopedEventBus()

    override fun dispose(disposeState: DisposeState) {
        this.disposeState = disposeState
        onChange.cancel()
        subscriptions.dispose()
    }

    override fun onChange(callback: (ObservableValueChanged<T>) -> Unit) = onChange.subscribeWithoutResult<ObservableValueChanged<T>> { callback(it) }
}
