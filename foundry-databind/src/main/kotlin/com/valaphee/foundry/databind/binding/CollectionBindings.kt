/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
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

infix fun <T : Any> ObservablePersistentCollection<T>.bindContainsWith(that: ObservableValue<T>) = ComputedDualBinding(this, that) { a, b -> a.contains(b) }

infix fun <T : Any> ObservablePersistentCollection<T>.bindContainsAllWith(that: ObservablePersistentCollection<T>) = ComputedDualBinding(this, that) { a, b -> a.containsAll(b) }

infix fun <T : Any> ObservablePersistentCollection<T>.bindIndexOfWith(that: ObservableValue<T>) = ComputedDualBinding(this, that) { a, b -> a.indexOf(b) }

infix fun <T : Any> ObservablePersistentCollection<T>.bindLastIndexOfWith(that: ObservableValue<T>) = ComputedDualBinding(this, that) { a, b -> a.lastIndexOf(b) }

infix fun <T : Any> ObservablePersistentCollection<T>.bindIsEqualToWith(that: ObservablePersistentCollection<T>) = ComputedDualBinding(this, that) { a, b -> a == b }

infix fun <T : Any> ObservableValue<PersistentList<T>>.bindPlusWith(that: ObservableValue<PersistentList<T>>) = ListBindingDecorator(ComputedDualBinding(this, that) { a, b -> a.addAll(b) })

infix fun <T : Any> ObservableValue<PersistentList<T>>.bindMinusWith(that: ObservableValue<PersistentList<T>>) = ListBindingDecorator(ComputedDualBinding(this, that) { a, b -> a.removeAll(b) })

infix fun <T : Any> ObservableValue<PersistentSet<T>>.bindPlusWith(that: ObservableValue<PersistentSet<T>>) = SetBindingDecorator(ComputedDualBinding(this, that) { a, b -> a.addAll(b) })

infix fun <T : Any> ObservableValue<PersistentSet<T>>.bindMinusWith(that: ObservableValue<PersistentSet<T>>) = SetBindingDecorator(ComputedDualBinding(this, that) { a, b -> a.removeAll(b) })
