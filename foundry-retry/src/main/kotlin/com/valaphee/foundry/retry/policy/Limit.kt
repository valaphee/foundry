/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

@file:JvmName("Limit")
package com.valaphee.foundry.retry.policy

import com.valaphee.foundry.retry.ContinueRetrying
import com.valaphee.foundry.retry.StopRetrying
import com.valaphee.foundry.retry.context.retryStatus
import com.valaphee.foundry.retry.sadd
import kotlin.coroutines.coroutineContext

fun limitAttempts(limit: Int): RetryPolicy<*> {
    require(limit > 0) { "limit must be positive: $limit" }

    return { if (coroutineContext.retryStatus.attempt + 1 >= limit) StopRetrying else ContinueRetrying }
}

fun <E> RetryPolicy<E>.limitByDelay(delay: Long): RetryPolicy<E> {
    require(delay > 0) { "delay must be positive: $delay" }

    return {
        when (val instruction = this.(this@limitByDelay)()) {
            StopRetrying, ContinueRetrying -> instruction
            else -> if (instruction.delay >= delay) StopRetrying else instruction
        }
    }
}

fun <E> RetryPolicy<E>.limitByCumulativeDelay(delay: Long): RetryPolicy<E> {
    require(delay > 0) { "delay must be positive: $delay" }

    return {
        when (val instruction = this.(this@limitByCumulativeDelay)()) {
            StopRetrying, ContinueRetrying -> instruction
            else -> if (coroutineContext.retryStatus.cumulativeDelay sadd instruction.delay >= delay) StopRetrying else instruction
        }
    }
}
