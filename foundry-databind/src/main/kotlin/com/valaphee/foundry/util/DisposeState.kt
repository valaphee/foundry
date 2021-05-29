/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.util

/**
 * @author Kevin Ludwig
 */
sealed class DisposeState(
    val isDisposed: Boolean
)

/**
 * @author Kevin Ludwig
 */
object NotDisposed : DisposeState(false)

/**
 * @author Kevin Ludwig
 */
object DisposedByHand : DisposeState(true)

/**
 * @author Kevin Ludwig
 */
data class DisposedByException(
    val exception: Exception
) : DisposeState(true)
