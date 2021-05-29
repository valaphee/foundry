/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.value

/**
 * @author Kevin Ludwig
 */
class ValueValidationFailedException(
    val newValue: Any,
    message: String
) : RuntimeException(message)
