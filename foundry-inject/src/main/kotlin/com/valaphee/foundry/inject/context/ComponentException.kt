/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

/**
 * @author Kevin Ludwig
 */
open class ComponentException(
    override val message: String? = null,
    override val cause: Throwable? = null
) : RuntimeException()
