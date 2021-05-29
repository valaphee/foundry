/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.event

/**
 * @author Kevin Ludwig
 */
interface Event {
    val key get() = checkNotNull(this::class.simpleName)

    val emitter: Any

    val trace: Iterable<Event> get() = listOf()
}
