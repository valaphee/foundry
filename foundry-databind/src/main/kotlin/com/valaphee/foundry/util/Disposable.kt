/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
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
