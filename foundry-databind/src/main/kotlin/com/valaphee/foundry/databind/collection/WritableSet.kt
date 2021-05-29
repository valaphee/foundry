/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.collection

import kotlinx.collections.immutable.PersistentSet

/**
 * @author Kevin Ludwig
 */
interface WritableSet<T : Any> : WritableCollection<T, PersistentSet<T>>
