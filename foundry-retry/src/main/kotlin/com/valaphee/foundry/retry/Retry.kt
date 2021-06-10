/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

@file:JvmName("Retry")

package com.valaphee.foundry.retry

import com.valaphee.foundry.retry.context.RetryStatus
import com.valaphee.foundry.retry.context.retryStatus
import com.valaphee.foundry.retry.policy.RetryPolicy
import com.valaphee.foundry.retry.policy.constantDelay
import com.valaphee.foundry.retry.policy.limitAttempts
import com.valaphee.foundry.retry.policy.plus
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@ExperimentalContracts
suspend fun <T> retry(policy: RetryPolicy<Throwable> = defaultPolicy, block: Producer<T>): T {
    contract {
        callsInPlace(policy, InvocationKind.UNKNOWN)
        callsInPlace(block, InvocationKind.AT_LEAST_ONCE)
    }

    return withContext(RetryStatus()) {
        lateinit var mostRecentFailure: Throwable
        while (true) {
            try {
                return@withContext block()
            } catch (ex: CancellationException) {
                throw ex
            } catch (t: Throwable) {
                val status = coroutineContext.retryStatus
                status.incrementAttempts()
                when (val instruction = RetryFailure(t).policy()) {
                    StopRetrying -> {
                        mostRecentFailure = t
                        break
                    }
                    ContinueRetrying -> {
                        status.previousDelay = 0
                        continue
                    }
                    else -> {
                        val delay = instruction.delay
                        delay(delay)
                        status.previousDelay = delay
                        status.incrementCumulativeDelay(delay)
                    }
                }
            }
        }
        throw mostRecentFailure
    }
}

@ExperimentalContracts
suspend fun <T> Producer<T>.retry(policy: RetryPolicy<Throwable> = defaultPolicy): T {
    contract {
        callsInPlace(policy, InvocationKind.UNKNOWN)
        callsInPlace(this@retry, InvocationKind.AT_LEAST_ONCE)
    }

    return retry(policy, this)
}

private val defaultPolicy: RetryPolicy<Throwable> = constantDelay(50) + limitAttempts(5)
private typealias Producer<T> = suspend () -> T
