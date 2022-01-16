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

