/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.collection

import com.valaphee.foundry.databind.value.ObservableValue
import kotlinx.collections.immutable.PersistentSet

/**
 * @author Kevin Ludwig
 */
interface ObservableSet<T : Any> : ObservableCollection<T, PersistentSet<T>>, PersistentSet<T>

typealias ObservablePersistentSet<T> = ObservableValue<PersistentSet<T>>
