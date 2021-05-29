/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.collection

import com.valaphee.foundry.databind.binding.Binding
import kotlinx.collections.immutable.PersistentList

/**
 * @author Kevin Ludwig
 */
interface ObservableListBinding<T : Any> : ObservableList<T>, Binding<PersistentList<T>>
