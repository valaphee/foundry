/*
 * Copyright (c) 2021-2022, Valaphee.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
