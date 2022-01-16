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

import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign
import kotlin.math.sqrt

fun <T> MutableList<T>.partition(k: Int, compare: (T, T) -> Int) = partition(indices, k, compare)

fun <T> MutableList<T>.partition(range: IntRange, k: Int, comparator: (T, T) -> Int) {
    partition(this, range.first, range.last, k, { get(it) }, comparator, { a, b -> this[a] = this[b].also { this[b] = this[a] } })
}

fun <T> Array<T>.partition(k: Int, compare: (T, T) -> Int) = partition(indices, k, compare)

fun <T> Array<T>.partition(range: IntRange, k: Int, compare: (T, T) -> Int) {
    partition(this, range.first, range.last, k, { get(it) }, compare, { a, b -> this[a] = this[b].also { this[b] = this[a] } })
}

fun <L, T> partition(elements: L, left: Int, right: Int, k: Int, get: L.(Int) -> T, compare: (T, T) -> Int, swap: L.(Int, Int) -> Unit) {
    var leftVar = left
    var rightVar = right
    while (rightVar > leftVar) {
        if (rightVar - leftVar > 600) {
            val n = rightVar - leftVar + 1
            val i = k - leftVar + 1
            val z = ln(n.toDouble())
            val s = 0.5 * exp(2.0 * z / 3.0)
            val sd = 0.5 * sqrt(z * s * (n - s) / n) * sign(i - n / 2.0)
            partition(elements, max(leftVar, (k - i * s / n + sd).toInt()), min(rightVar, (k + (n - i) * s / n + sd).toInt()), k, get, compare, swap)
        }
        val t = elements.get(k)
        var i = leftVar
        var j = rightVar
        elements.swap(leftVar, k)
        if (compare(elements.get(rightVar), t) > 0) elements.swap(rightVar, leftVar)
        while (i < j) {
            elements.swap(i, j)
            i++
            j--
            while (compare(elements.get(i), t) < 0) i++
            while (j >= 0 && compare(elements.get(j), t) > 0) j--
        }
        if (compare(elements.get(leftVar), t) == 0) elements.swap(leftVar, j) else {
            j++
            elements.swap(j, rightVar)
        }
        if (j <= k) leftVar = j + 1
        if (k <= j) rightVar = j - 1
    }
}
