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

import com.valaphee.foundry.databind.value.ObservableValue
import com.valaphee.foundry.util.Disposable
import com.valaphee.foundry.util.DisposedByException

/**
 * @author Kevin Ludwig
 */
interface Binding<out T : Any> : ObservableValue<T>, Disposable

internal fun <T : Any> Binding<T>.runWithDisposeOnFailure(function: () -> Unit) {
    try {
        function()
    } catch (ex: Exception) {
        dispose(DisposedByException(ex))
    }
}
