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
