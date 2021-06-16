/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.retry.policy

import com.valaphee.foundry.retry.ContinueRetrying
import com.valaphee.foundry.retry.RetryFailure
import com.valaphee.foundry.retry.RetryInstruction
import com.valaphee.foundry.retry.StopRetrying
import com.valaphee.foundry.retry.retryAfter
import kotlin.math.max

typealias RetryPolicy<E> = suspend RetryFailure<E>.() -> RetryInstruction

operator fun <E> RetryPolicy<E>.plus(other: RetryPolicy<E>): RetryPolicy<E> = {
    val a = this@plus(this)
    val b = other(this)
    when {
        a == StopRetrying || b == StopRetrying -> StopRetrying
        a == ContinueRetrying && b == ContinueRetrying -> ContinueRetrying
        else -> retryAfter(max(a.delay, b.delay))
    }
}
