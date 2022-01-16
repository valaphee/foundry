/*
 * Copyright (c) 2021-2022, Valaphee.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.valaphee.foundry.math

import kotlin.math.nextDown
import kotlin.random.Random

val defaultRandom = Random(System.currentTimeMillis().toInt())

fun Random.nextFloat(until: Float): Float = nextFloat(0.0f, until)

fun Random.nextFloat(from: Float, until: Float): Float {
    require(until > from) { "Range is empty: [$from, $until)" }
    val size = until - from
    val value = if (size.isInfinite() && from.isFinite() && until.isFinite()) {
        val r1 = nextFloat() * (until / 2 - from / 2)
        from + r1 + r1
    } else from + nextFloat() * size
    return if (value >= until) until.nextDown() else value
}
