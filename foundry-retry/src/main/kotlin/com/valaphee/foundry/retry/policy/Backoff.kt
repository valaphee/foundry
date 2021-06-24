/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

@file:Suppress("NOTHING_TO_INLINE")

package com.valaphee.foundry.retry.policy

import com.valaphee.foundry.retry.ContinueRetrying
import com.valaphee.foundry.retry.context.retryRandom
import com.valaphee.foundry.retry.context.retryStatus
import com.valaphee.foundry.retry.exp2
import com.valaphee.foundry.retry.retryAfter
import com.valaphee.foundry.retry.sadd
import com.valaphee.foundry.retry.smul
import kotlin.coroutines.coroutineContext
import kotlin.math.max
import kotlin.math.min

fun binaryExponentialBackoff(base: Long, maximum: Long): RetryPolicy<*> {
    require(base > 0) { "base must be positive: $base" }
    require(maximum > 0) { "maximum must be positive: $maximum" }

    return { retryAfter(min(maximum, base smul coroutineContext.retryStatus.attempt.exp2())) }
}

inline fun binaryExponentialBackoff(range: LongRange) = binaryExponentialBackoff(range.first, range.last)

fun fullJitterBackoff(base: Long, maximum: Long): RetryPolicy<*> {
    require(base > 0) { "base must be positive: $base" }
    require(maximum > 0) { "maximum must be positive: $maximum" }

    return {
        val randomDelay = coroutineContext.retryRandom.random.nextLong(min(maximum, base smul coroutineContext.retryStatus.attempt.exp2()) sadd 1)
        if (randomDelay == 0L) ContinueRetrying else retryAfter(randomDelay)
    }
}

inline fun fullJitterBackoff(range: LongRange) = fullJitterBackoff(range.first, range.last)

fun equalJitterBackoff(base: Long, maximum: Long): RetryPolicy<*> {
    require(base > 0) { "base must be positive: $base" }
    require(maximum > 0) { "maximum must be positive: $maximum" }

    return {
        val delay = min(maximum, base smul coroutineContext.retryStatus.attempt.exp2())
        retryAfter((delay / 2) sadd coroutineContext.retryRandom.random.nextLong((delay / 2) sadd 1))
    }
}

inline fun equalJitterBackoff(range: LongRange) = equalJitterBackoff(range.first, range.last)

fun decorrelatedJitterBackoff(base: Long, maximum: Long): RetryPolicy<*> {
    require(base > 0) { "base must be positive: $base" }
    require(maximum > 0) { "maximum must be positive: $maximum" }

    return { retryAfter(min(maximum, coroutineContext.retryRandom.random.nextLong(base, max(base, coroutineContext.retryStatus.prevDelay smul 3) sadd 1))) }
}

inline fun decorrelatedJitterBackoff(range: LongRange) = decorrelatedJitterBackoff(range.first, range.last)
