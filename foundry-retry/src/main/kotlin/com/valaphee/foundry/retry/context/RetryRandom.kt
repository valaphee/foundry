/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.retry.context

import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

val CoroutineContext.retryRandom get() = get(RetryRandom) ?: default

/**
 * @author Kevin Ludwig
 */
class RetryRandom(
    val random: Random = Random.Default
) : AbstractCoroutineContextElement(RetryRandom) {
    companion object Key : CoroutineContext.Key<RetryRandom>

    override fun toString() = "RetryRandom($random)"
}

private val default = RetryRandom()
