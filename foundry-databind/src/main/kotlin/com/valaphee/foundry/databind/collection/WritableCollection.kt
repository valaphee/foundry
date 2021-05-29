/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.collection

import com.valaphee.foundry.databind.value.WritableValue
import kotlinx.collections.immutable.PersistentCollection

/**
 * @author Kevin Ludwig
 */
interface WritableCollection<T : Any, C : PersistentCollection<T>> : WritableValue<C>
