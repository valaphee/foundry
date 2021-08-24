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

import com.valaphee.foundry.databind.value.ObservableValue
import com.valaphee.foundry.event.Event

/**
 * @author Kevin Ludwig
 */
data class ObservableValueChanged<out T : Any>(
    val oldValue: T,
    val newValue: T,
    val observableValue: ObservableValue<T>,
    override val emitter: Any,
    override val trace: Iterable<Event> = listOf(),
    val type: ChangeType = ScalarChange,
) : Event

/**
 * @author Kevin Ludwig
 */
sealed class ChangeType

/**
 * @author Kevin Ludwig
 */
object ScalarChange : ChangeType()

/**
 * @author Kevin Ludwig
 */
sealed class ListChange : ChangeType()

/**
 * @author Kevin Ludwig
 */
data class ListAdd<T : Any>(
    val element: T
) : ListChange()

/**
 * @author Kevin Ludwig
 */
data class ListAddAt<T : Any>(
    val index: Int,
    val element: T
) : ListChange()

/**
 * @author Kevin Ludwig
 */
data class ListRemove<T : Any>(
    val element: T
) : ListChange()

/**
 * @author Kevin Ludwig
 */
data class ListRemoveAt(
    val index: Int
) : ListChange()

/**
 * @author Kevin Ludwig
 */
data class ListSet<T : Any>(
    val index: Int,
    val element: T
) : ListChange()

/**
 * @author Kevin Ludwig
 */
data class ListAddAll<T : Any>(
    val elements: Collection<T>
) : ListChange()

/**
 * @author Kevin Ludwig
 */
data class ListAddAllAt<T : Any>(
    val index: Int,
    val elements: Collection<T>
) : ListChange()

/**
 * @author Kevin Ludwig
 */
data class ListRemoveAll<T : Any>(
    val elements: Collection<T>
) : ListChange()

/**
 * @author Kevin Ludwig
 */
data class ListRemoveAllWhen<T : Any>(
    val predicate: (T) -> Boolean
) : ListChange()

/**
 * @author Kevin Ludwig
 */
object ListClear : ListChange()
