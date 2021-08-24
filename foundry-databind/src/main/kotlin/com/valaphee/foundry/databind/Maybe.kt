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
