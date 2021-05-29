/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.collection

import com.valaphee.foundry.databind.value.ObservableValue
import kotlinx.collections.immutable.PersistentMap

/**
 * @author Kevin Ludwig
 */
interface ObservableMap<K : Any, V : Any> : PersistentMap<K, V>, ObservableValue<PersistentMap<K, V>>

typealias ObservablePersistentMap<K, V> = ObservableValue<PersistentMap<K, V>>
