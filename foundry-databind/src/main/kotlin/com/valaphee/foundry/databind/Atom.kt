/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind

/**
 * @author Kevin Ludwig
 */
interface Atom<T : Any> {
    fun get(): T

    fun transform(transformer: (T) -> T): T

    companion object {
        fun <T : Any> fromObject(`object`: T): Atom<T> = DefaultAtom(`object`)
    }
}

fun <T : Any> T.toAtom() = Atom.fromObject(this)

/**
 * @author Kevin Ludwig
 */
class DefaultAtom<T : Any>(
    initialValue: T
) : Atom<T> {
    @Volatile
    private var value = initialValue

    override fun get() = value

    @Synchronized
    override fun transform(transformer: (T) -> T): T {
        value = transformer(value)
        return value
    }
}
