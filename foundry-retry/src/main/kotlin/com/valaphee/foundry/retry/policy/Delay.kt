/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

@file:JvmName("Delay")
package com.valaphee.foundry.retry.policy

import com.valaphee.foundry.retry.ContinueRetrying
import com.valaphee.foundry.retry.StopRetrying
import com.valaphee.foundry.retry.retryAfter
import kotlin.math.min

fun constantDelay(delay: Long): RetryPolicy<*> {
    require(delay > 0) { "delay must be positive: $delay" }

    return { retryAfter(delay) }
}

fun <E> RetryPolicy<E>.maxDelay(delay: Long): RetryPolicy<E> {
    require(delay > 0) { "delay must be positive: $delay" }

    return {
        when (val instruction = this.(this@maxDelay)()) {
            StopRetrying, ContinueRetrying -> instruction
            else -> retryAfter(min(instruction.delay, delay))
        }
    }
}
