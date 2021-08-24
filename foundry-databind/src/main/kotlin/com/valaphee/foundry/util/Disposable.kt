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
