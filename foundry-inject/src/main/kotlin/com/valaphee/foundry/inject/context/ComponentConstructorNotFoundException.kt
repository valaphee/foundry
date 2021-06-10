/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

/**
 * @author Kevin Ludwig
 */
class ComponentConstructorNotFoundException(
    message: String? = null,
    cause: Throwable? = null
) : ComponentLoadException(message, cause)
