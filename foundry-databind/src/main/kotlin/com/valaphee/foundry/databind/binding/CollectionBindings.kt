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

package com.valaphee.foundry.databind.binding

import com.valaphee.foundry.databind.collection.ListBindingDecorator
import com.valaphee.foundry.databind.collection.ObservableListBinding
import com.valaphee.foundry.databind.collection.ObservablePersistentCollection
import com.valaphee.foundry.databind.collection.SetBindingDecorator
import com.valaphee.foundry.databind.value.ObservableValue
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentListOf

fun <T : Any> ObservablePersistentCollection<T>.bindSize() = ComputedBinding(this) { it.size }

fun <T : Any> ObservablePersistentCollection<ObservablePersistentCollection<T>>.bindFlatten(): ObservableListBinding<T> = ListBindingDecorator(ComputedBinding(this) {
    var result = persistentListOf<T>()
    it.forEach { result = result.addAll(it.value) }
    result
})

fun <T : Any> ObservablePersistentCollection<T>.bindIsEmpty() = ComputedBinding(this) { it.isEmpty() }

infix fun <T : Any> ObservablePersistentCollection<T>.bindContainsWith(b: ObservableValue<T>) = ComputedDualBinding(this, b) { a, b -> a.contains(b) }

infix fun <T : Any> ObservablePersistentCollection<T>.bindContainsAllWith(b: ObservablePersistentCollection<T>) = ComputedDualBinding(this, b) { a, b -> a.containsAll(b) }

infix fun <T : Any> ObservablePersistentCollection<T>.bindIndexOfWith(b: ObservableValue<T>) = ComputedDualBinding(this, b) { a, b -> a.indexOf(b) }

infix fun <T : Any> ObservablePersistentCollection<T>.bindLastIndexOfWith(b: ObservableValue<T>) = ComputedDualBinding(this, b) { a, b -> a.lastIndexOf(b) }

infix fun <T : Any> ObservablePersistentCollection<T>.bindIsEqualToWith(b: ObservablePersistentCollection<T>) = ComputedDualBinding(this, b) { a, b -> a == b }

infix fun <T : Any> ObservableValue<PersistentList<T>>.bindPlusWith(b: ObservableValue<PersistentList<T>>) = ListBindingDecorator(ComputedDualBinding(this, b) { a, b -> a.addAll(b) })

infix fun <T : Any> ObservableValue<PersistentList<T>>.bindMinusWith(b: ObservableValue<PersistentList<T>>) = ListBindingDecorator(ComputedDualBinding(this, b) { a, b -> a.removeAll(b) })

infix fun <T : Any> ObservableValue<PersistentSet<T>>.bindPlusWith(b: ObservableValue<PersistentSet<T>>) = SetBindingDecorator(ComputedDualBinding(this, b) { a, b -> a.addAll(b) })

infix fun <T : Any> ObservableValue<PersistentSet<T>>.bindMinusWith(b: ObservableValue<PersistentSet<T>>) = SetBindingDecorator(ComputedDualBinding(this, b) { a, b -> a.removeAll(b) })
