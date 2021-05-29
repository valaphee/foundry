/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.collection

import com.valaphee.foundry.databind.value.ObservableValue
import kotlinx.collections.immutable.PersistentList

/**
 * @author Kevin Ludwig
 */
interface ObservableList<T : Any> : ObservableCollection<T, PersistentList<T>>, ObservableValue<PersistentList<T>>, PersistentList<T>

typealias ObservablePersistentList<T> = ObservableValue<PersistentList<T>>
