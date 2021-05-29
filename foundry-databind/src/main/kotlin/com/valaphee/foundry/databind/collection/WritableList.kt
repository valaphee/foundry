/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.collection

import kotlinx.collections.immutable.PersistentList

/**
 * @author Kevin Ludwig
 */
interface WritableList<T : Any> : WritableCollection<T, PersistentList<T>>
