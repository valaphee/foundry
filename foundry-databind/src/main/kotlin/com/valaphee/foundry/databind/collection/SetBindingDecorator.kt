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
