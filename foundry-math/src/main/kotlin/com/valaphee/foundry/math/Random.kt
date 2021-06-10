/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

@file:JvmName("Random")

package com.valaphee.foundry.math

import kotlin.math.nextDown
import kotlin.random.Random

val defaultRandom = Random(System.currentTimeMillis().toInt())

fun Random.nextFloat(until: Float): Float = nextFloat(0.0f, until)

fun Random.nextFloat(from: Float, until: Float): Float {
    checkRangeBounds(from, until)
    val size = until - from
    val r = if (size.isInfinite() && from.isFinite() && until.isFinite()) {
        val r1 = nextFloat() * (until / 2 - from / 2)
        from + r1 + r1
    } else from + nextFloat() * size
    return if (r >= until) until.nextDown() else r
}

private fun checkRangeBounds(from: Float, until: Float) = require(until > from) { boundsErrorMessage(from, until) }

private fun boundsErrorMessage(from: Any, until: Any) = "Random range is empty: [$from, $until)"
