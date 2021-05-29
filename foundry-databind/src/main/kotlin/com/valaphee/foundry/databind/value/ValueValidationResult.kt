/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.value

/**
 * @author Kevin Ludwig
 */
sealed class ValueValidationResult<T : Any>(
    val successful: Boolean
) {
    abstract val value: T
}

/**
 * @author Kevin Ludwig
 */
data class ValueValidationFailed<T : Any>(
    override val value: T,
    val cause: ValueValidationFailedException
) : ValueValidationResult<T>(false)

/**
 * @author Kevin Ludwig
 */
data class ValueValidationSuccessful<T : Any>(
    override val value: T
) : ValueValidationResult<T>(true)
