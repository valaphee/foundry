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
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.toPersistentMap

/**
 * @author Kevin Ludwig
 */
interface MapProperty<K : Any, V : Any> : ObservableMap<K, V>, WritableMap<K, V>, Property<PersistentMap<K, V>>

/**
 * @author Kevin Ludwig
 */
@Suppress("UNCHECKED_CAST")
class DefaultMapProperty<K : Any, V : Any>(
    initialValue: Map<K, V>,
    validate: Predicate<Map<K, V>> = { true }
) : DefaultProperty<PersistentMap<K, V>>(initialValue.toPersistentMap(), validate), MapProperty<K, V> {
    override val entries get() = value.entries

    override val keys get() = value.keys

    override val size get() = value.size

    override val values get() = value.values

    override fun containsKey(key: K) = value.containsKey(key)

    override fun containsValue(value: V) = this.value.containsValue(value)

    override fun get(key: K): V? = value[key]

    override fun isEmpty() = value.isEmpty()

    override fun clear() = updateCurrentValue { it.clear() }

    override fun put(key: K, value: V) = updateCurrentValue { it.put(key, value) }

    override fun putAll(m: Map<out K, V>) = updateCurrentValue { it.putAll(m) }

    override fun remove(key: K) = updateCurrentValue { it.remove(key) }

    override fun remove(key: K, value: V) = updateCurrentValue { it.remove(key, value) }

    override fun builder() = value.builder()
}
