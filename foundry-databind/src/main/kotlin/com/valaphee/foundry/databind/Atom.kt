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
