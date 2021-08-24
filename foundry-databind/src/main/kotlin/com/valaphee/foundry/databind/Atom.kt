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
