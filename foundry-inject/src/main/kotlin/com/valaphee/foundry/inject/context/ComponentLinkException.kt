/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

/**
 * @author Kevin Ludwig
 */
open class ComponentLinkException(
    message: String? = null,
    cause: Throwable? = null
) : ComponentException(message, cause)
