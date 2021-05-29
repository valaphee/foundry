/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.collection

import com.valaphee.foundry.databind.value.ObservableValue
import kotlinx.collections.immutable.PersistentCollection

/**
 * @author Kevin Ludwig
 */
interface ObservableCollection<T : Any, C : PersistentCollection<T>> : PersistentCollection<T>, ObservableValue<C>

typealias ObservablePersistentCollection<T> = ObservableValue<PersistentCollection<T>>
