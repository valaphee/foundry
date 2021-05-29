/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.collection

import com.valaphee.foundry.databind.property.DefaultProperty
import com.valaphee.foundry.databind.property.Property
import com.valaphee.foundry.util.Predicate
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.toPersistentSet

/**
 * @author Kevin Ludwig
 */
interface SetProperty<T : Any> : ObservableSet<T>, WritableSet<T>, ObservableCollection<T, PersistentSet<T>>, Property<PersistentSet<T>>

/**
 * @author Kevin Ludwig
 */
@Suppress("UNCHECKED_CAST")
class DefaultSetProperty<T : Any>(
    initialValue: Set<T>,
    validate: Predicate<Set<T>> = { true }
) : DefaultProperty<PersistentSet<T>>(initialValue.toPersistentSet(), validate), SetProperty<T> {
    override val size get() = value.size

    override fun contains(element: T) = value.contains(element)

    override fun containsAll(elements: Collection<T>) = value.containsAll(elements)

    override fun isEmpty() = value.isEmpty()

    override fun iterator() = value.iterator()

    override fun add(element: T) = updateCurrentValue { it.add(element) }

    override fun addAll(elements: Collection<T>) = updateCurrentValue { it.addAll(elements) }

    override fun clear() = updateCurrentValue { it.clear() }

    override fun remove(element: T) = updateCurrentValue { it.remove(element) }

    override fun removeAll(predicate: (T) -> Boolean) = updateCurrentValue { it.removeAll(predicate) }

    override fun removeAll(elements: Collection<T>) = updateCurrentValue { it.removeAll(elements) }

    override fun retainAll(elements: Collection<T>) = TODO()

    override fun builder() = value.builder()
}

