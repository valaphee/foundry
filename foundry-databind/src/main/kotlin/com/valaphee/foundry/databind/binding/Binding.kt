/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

@file:JvmName("Bindings")

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
