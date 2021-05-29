/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.collection

import com.valaphee.foundry.databind.binding.Binding
import kotlinx.collections.immutable.PersistentSet

/**
 * @author Kevin Ludwig
 */
interface ObservableSetBinding<T : Any> : ObservableSet<T>, Binding<PersistentSet<T>>
