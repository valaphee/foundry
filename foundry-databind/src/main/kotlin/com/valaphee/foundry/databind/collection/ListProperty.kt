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
