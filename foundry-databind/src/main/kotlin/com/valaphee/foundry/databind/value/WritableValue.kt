/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.value

import com.valaphee.foundry.databind.binding.Binding

/**
 * @author Kevin Ludwig
 */
interface WritableValue<T : Any> : Value<T> {
    override var value: T

    fun updateValue(newValue: T): ValueValidationResult<T>

    fun transformValue(transform: (T) -> T): ValueValidationResult<T>

    fun updateFrom(observable: ObservableValue<T>, updateWhenBound: Boolean = true): Binding<T>

    fun <S : Any> updateFrom(observable: ObservableValue<S>, updateWhenBound: Boolean = true, convert: (S) -> T): Binding<T>
}
