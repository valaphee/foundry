/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.retry

/**
 * @author Kevin Ludwig
 */
@JvmInline
value class RetryInstruction @PublishedApi internal constructor(
    val delay: Long
)

val StopRetrying = RetryInstruction(-1L)
val ContinueRetrying = RetryInstruction(0L)

@Suppress("NOTHING_TO_INLINE", "FunctionName")
inline fun retryAfter(delay: Long): RetryInstruction {
    require(delay > 0) { "delay must be positive: $delay" }

    return RetryInstruction(delay)
}
