/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.collection

import com.valaphee.foundry.databind.property.Property
import kotlinx.collections.immutable.PersistentCollection

/**
 * @author Kevin Ludwig
 */
interface CollectionProperty<T : Any, C : PersistentCollection<T>> : ObservableCollection<T, C>, WritableCollection<T, C>, Property<C>
