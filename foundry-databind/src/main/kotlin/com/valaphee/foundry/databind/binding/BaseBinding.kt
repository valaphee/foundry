/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.binding

import com.valaphee.foundry.databind.ObservableValueChanged
import com.valaphee.foundry.databind.property.InternalProperty
import com.valaphee.foundry.databind.value.ObservableValue
import com.valaphee.foundry.event.Subscriptions
import com.valaphee.foundry.event.UnscopedEventBus
import com.valaphee.foundry.event.dispose
import com.valaphee.foundry.event.subscribeWithoutResult
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

    protected val onChange = UnscopedEventBus.create()

    override fun dispose(disposeState: DisposeState) {
        this.disposeState = disposeState
        onChange.cancel()
        subscriptions.dispose()
    }

    override fun onChange(callback: (ObservableValueChanged<T>) -> Unit) = onChange.subscribeWithoutResult<ObservableValueChanged<T>> { callback(it) }
}
