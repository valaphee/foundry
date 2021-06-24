/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.retry.context

import com.valaphee.foundry.retry.sadd
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

val CoroutineContext.retryStatus get() = get(RetryStatus) ?: error("No RetryStatus in context")

/**
 * @author Kevin Ludwig
 */
class RetryStatus(
    attempt: Int = 0,
    prevDelay: Long = 0,
    cumulativeDelay: Long = 0
) : AbstractCoroutineContextElement(RetryStatus) {
    companion object Key : CoroutineContext.Key<RetryStatus>

    var attempt = attempt
        private set(value) {
            require(value >= 0) { "attempt must be non-negative: $attempt" }

            field = value
        }

    var prevDelay = prevDelay
        internal set(value) {
            require(value >= 0) { "prevDelay must be non-negative: $prevDelay" }

            field = value
        }

    var cumulativeDelay = cumulativeDelay
        private set(value) {
            require(value >= 0) { "cumulativeDelay must be non-negative: $cumulativeDelay" }

            field = value
        }

    internal fun incrementAttempts() {
        attempt++
    }

    internal fun incrementCumulativeDelay(delay: Long) {
        require(delay > 0) { "delay must be positive: $delay" }

        cumulativeDelay = cumulativeDelay sadd delay
    }

    override fun toString() = "RetryStatus(attempt=$attempt, prevDelay=$prevDelay, cumulativeDelay=$cumulativeDelay)"
}
