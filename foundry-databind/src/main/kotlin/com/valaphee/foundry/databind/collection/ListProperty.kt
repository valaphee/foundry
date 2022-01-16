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

package com.valaphee.foundry.databind.collection

import com.valaphee.foundry.databind.ListAdd
import com.valaphee.foundry.databind.ListAddAll
import com.valaphee.foundry.databind.ListAddAllAt
import com.valaphee.foundry.databind.ListAddAt
import com.valaphee.foundry.databind.ListClear
import com.valaphee.foundry.databind.ListRemove
import com.valaphee.foundry.databind.ListRemoveAll
import com.valaphee.foundry.databind.ListRemoveAllWhen
import com.valaphee.foundry.databind.ListRemoveAt
import com.valaphee.foundry.databind.ListSet
import com.valaphee.foundry.databind.property.DefaultProperty
import com.valaphee.foundry.databind.property.Property
import com.valaphee.foundry.util.Predicate
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

/**
 * @author Kevin Ludwig
 */
interface ListProperty<T : Any> : ObservableList<T>, WritableList<T>, ObservableCollection<T, PersistentList<T>>, Property<PersistentList<T>>

/**
 * @author Kevin Ludwig
 */
@Suppress("UNCHECKED_CAST")
class DefaultListProperty<T : Any>(
    initialValue: List<T>,
    validate: Predicate<List<T>> = { true }
) : DefaultProperty<PersistentList<T>>(initialValue.toPersistentList(), validate), ListProperty<T> {
    override val size get() = value.size

    override fun contains(element: T) = value.contains(element)

    override fun containsAll(elements: Collection<T>) = value.containsAll(elements)

    override fun get(index: Int) = value[index]

    override fun indexOf(element: T) = value.indexOf(element)

    override fun isEmpty() = value.isEmpty()

    override fun iterator() = value.iterator()

    override fun lastIndexOf(element: T) = value.lastIndexOf(element)

    override fun listIterator() = value.listIterator()

    override fun listIterator(index: Int) = value.listIterator(index)

    override fun subList(fromIndex: Int, toIndex: Int) = value.subList(fromIndex, toIndex)

    override fun builder() = value.builder()

    override fun add(element: T) = updateCurrentValue(ListAdd(element)) { it.add(element) }

    override fun add(index: Int, element: T) = updateCurrentValue(ListAddAt(index, element)) { it.add(index, element) }

    override fun set(index: Int, element: T) = updateCurrentValue(ListSet(index, element)) { it.set(index, element) }

    override fun remove(element: T) = updateCurrentValue(ListRemove(element)) { it.remove(element) }

    override fun removeAt(index: Int) = updateCurrentValue(ListRemoveAt(index)) { it.removeAt(index) }

    override fun addAll(elements: Collection<T>) = updateCurrentValue(ListAddAll(elements)) { it.addAll(elements) }

    override fun addAll(index: Int, c: Collection<T>) = updateCurrentValue(ListAddAllAt(index, c)) { it.addAll(index, c) }

    override fun removeAll(elements: Collection<T>) = updateCurrentValue(ListRemoveAll(elements)) { it.removeAll(elements) }

    override fun removeAll(predicate: (T) -> Boolean) = updateCurrentValue(ListRemoveAllWhen(predicate)) { it.removeAll(predicate) }

    override fun retainAll(elements: Collection<T>) = TODO()

    override fun clear() = updateCurrentValue(ListClear) { it.clear() }
}
