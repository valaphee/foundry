/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
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
