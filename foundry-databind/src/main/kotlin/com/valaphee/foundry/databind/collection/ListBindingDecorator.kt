/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

@file:Suppress("UNUSED_PARAMETER")

package com.valaphee.foundry.databind.collection

import com.valaphee.foundry.databind.ObservableValueChanged
import com.valaphee.foundry.databind.binding.Binding
import com.valaphee.foundry.util.DisposeState
import kotlinx.collections.immutable.PersistentList

/**
 * @author Kevin Ludwig
 */
@Suppress("UNCHECKED_CAST")
class ListBindingDecorator<T : Any>(
    private val binding: Binding<PersistentList<T>>
) : ObservableListBinding<T> {
    override val value get() = binding.value

    override val size get() = value.size

    override fun onChange(callback: (ObservableValueChanged<PersistentList<T>>) -> Unit) = binding.onChange(callback)

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

    override fun add(element: T) = value.add(element)

    override fun remove(element: T) = value.remove(element)

    override fun addAll(elements: Collection<T>) = value.addAll(elements)

    override fun addAll(index: Int, c: Collection<T>) = value.addAll(index, c)

    override fun removeAll(elements: Collection<T>) = value.removeAll(elements)

    override fun removeAll(predicate: (T) -> Boolean) = value.removeAll(predicate)

    override fun retainAll(elements: Collection<T>) = TODO()

    override fun clear() = value.clear()

    override fun set(index: Int, element: T) = value.set(index, element)

    override fun add(index: Int, element: T) = value.add(index, element)

    override fun removeAt(index: Int) = value.removeAt(index)

    override fun builder() = value.builder()

    override val disposeState get() = binding.disposeState

    override fun dispose(disposeState: DisposeState) = binding.dispose(disposeState)
}
