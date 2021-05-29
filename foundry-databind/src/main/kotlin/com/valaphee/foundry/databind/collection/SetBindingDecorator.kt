/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

@file:Suppress("UNUSED_PARAMETER")

package com.valaphee.foundry.databind.collection

import com.valaphee.foundry.databind.ObservableValueChanged
import com.valaphee.foundry.databind.binding.Binding
import com.valaphee.foundry.util.DisposeState
import kotlinx.collections.immutable.PersistentSet

/**
 * @author Kevin Ludwig
 */
@Suppress("UNCHECKED_CAST")
class SetBindingDecorator<T : Any>(
    private val binding: Binding<PersistentSet<T>>
) : ObservableSetBinding<T> {
    override val value get() = binding.value

    override val size get() = value.size

    override fun onChange(callback: (ObservableValueChanged<PersistentSet<T>>) -> Unit) = binding.onChange(callback)

    override fun contains(element: T) = value.contains(element)

    override fun containsAll(elements: Collection<T>) = value.containsAll(elements)

    override fun isEmpty() = value.isEmpty()

    override fun iterator() = value.iterator()

    override fun add(element: T) = value.add(element)

    override fun remove(element: T) = value.remove(element)

    override fun addAll(elements: Collection<T>) = value.addAll(elements)

    override fun removeAll(elements: Collection<T>) = value.removeAll(elements)

    override fun removeAll(predicate: (T) -> Boolean) = value.removeAll(predicate)

    override fun retainAll(elements: Collection<T>) = TODO()

    override fun clear() = value.clear()

    override fun builder() = value.builder()

    override val disposeState get() = binding.disposeState

    override fun dispose(disposeState: DisposeState) = binding.dispose(disposeState)
}
