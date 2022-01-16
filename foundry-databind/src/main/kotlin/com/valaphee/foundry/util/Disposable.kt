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

package com.valaphee.foundry.util

import com.valaphee.foundry.databind.value.ObservableValue
import com.valaphee.foundry.event.Subscription

/**
 * @author Kevin Ludwig
 */
interface Disposable {
    val disposed get() = disposeState.isDisposed
    val disposeState: DisposeState

    fun dispose(disposeState: DisposeState = DisposedByHand)

    infix fun disposeWhen(condition: ObservableValue<Boolean>) {
        if (condition.value) dispose() else {
            var subscription: Subscription? = null
            subscription = condition.onChange { (_, newValue) ->
                if (newValue) {
                    subscription?.dispose()
                    dispose()
                }
            }
        }
    }

    infix fun keepWhile(condition: ObservableValue<Boolean>) {
        if (condition.value.not()) dispose() else {
            var subscription: Subscription? = null
            subscription = condition.onChange { (_, newValue) ->
                if (newValue.not()) {
                    subscription?.dispose()
                    dispose()
                }
            }
        }
    }
}
