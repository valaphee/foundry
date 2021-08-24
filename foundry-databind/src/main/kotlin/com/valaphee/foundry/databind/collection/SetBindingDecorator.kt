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
