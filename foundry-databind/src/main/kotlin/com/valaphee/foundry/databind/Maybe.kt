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

package com.valaphee.foundry.databind

import com.valaphee.foundry.util.Predicate

/**
 * @author Kevin Ludwig
 */
class Maybe<T> {
    private val value: T?

    val isPresent get() = value != null

    fun isEmpty() = isPresent.not()

    private constructor() {
        this.value = null
    }

    private constructor(value: T) {
        this.value = value
    }

    fun get(): T {
        if (value == null) throw NoSuchElementException("No value present")
        return value
    }

    fun ifPresent(consumer: (T) -> Unit) {
        if (value != null) consumer(value)
    }

    fun filter(predicate: Predicate<in T>) = if (!isPresent) this else if (predicate(get())) this else empty()

    fun <U> map(mapper: (T) -> U) = if (isEmpty()) empty() else ofNullable(mapper.invoke(get()))

    fun <U> flatMap(mapper: (T) -> Maybe<U>) = if (isEmpty()) empty() else mapper.invoke(get())

    fun <U> fold(whenEmpty: () -> U, whenPresent: (T) -> U) = if (isPresent) whenPresent.invoke(get()) else whenEmpty()

    fun orElse(other: T) = value ?: other

    fun orElseGet(other: () -> T) = value ?: other()

    fun <X : Throwable> orElseThrow(exceptionSupplier: () -> X): T = value ?: throw exceptionSupplier()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Maybe<*>

        if (value != other.value) return false

        return true
    }

    override fun hashCode() = value?.hashCode() ?: 0

    companion object {
        private val empty = Maybe<Any>()

        @Suppress("UNCHECKED_CAST")
        fun <T> empty() = empty as Maybe<T>

        fun <T> of(value: T) = Maybe(value)

        fun <T> ofNullable(value: T?) = if (value == null) empty() else of(value)
    }
}
