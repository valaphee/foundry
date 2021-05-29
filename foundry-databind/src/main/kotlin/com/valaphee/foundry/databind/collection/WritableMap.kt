/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.collection

import com.valaphee.foundry.databind.value.WritableValue
import kotlinx.collections.immutable.PersistentMap

/**
 * @author Kevin Ludwig
 */
interface WritableMap<K : Any, V : Any> : PersistentMap<K, V>, WritableValue<PersistentMap<K, V>>
