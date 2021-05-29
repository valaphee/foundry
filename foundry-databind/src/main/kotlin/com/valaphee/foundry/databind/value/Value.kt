/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.value

/**
 * @author Kevin Ludwig
 */
interface Value<out T : Any> {
    val value: T
}
