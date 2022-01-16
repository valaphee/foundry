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
