/*
 * MIT License
 *
 * Copyright (c) 2021, Valaphee.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.valaphee.foundry.math

import kotlin.math.PI
import kotlin.math.acos

/**
 * @author Kevin Ludwig
 */
open class BSpline<T>(
    var degree: Int,
    private val create: () -> T,
    private val copy: (source: T, destination: T) -> Unit,
    private val mix: (w0: Float, p0: T, w1: Float, p1: T, result: T) -> Unit
) {
    val points = mutableListOf<T>()
    private val d = mutableListOf<T>()

    fun addInterpolationEndpoints() {
        for (i in 0 until degree - 1) {
            points.add(0, points.first())
            points += points.last()
        }
    }

    fun evaluate(x: Float, result: T): T {
        when {
            x <= 0.0f -> copy(points.first(), result)
            x >= 1.0f -> copy(points.last(), result)
            else -> {
                if (d.size != degree + 1) {
                    d.clear()
                    for (i in 0..degree) d += create()
                }
                var xLin = x
                for (i in 0 until degree - 2) xLin = (1.0f - acos((x - 0.5f) * 2.0f) / PI).toFloat()
                val xx = degree + xLin * (points.size - degree)
                val kk = xx.toInt().clamp(degree, points.size - 1)
                for (j in 0..degree) copy(points[j + kk - degree], d[j])
                for (r in 1..degree) {
                    for (j in degree downTo r) {
                        val alpha = (xx - (j + kk - degree).toFloat()) / ((j + 1 + kk - r).toFloat() - (j + kk - degree).toFloat())
                        mix(1.0f - alpha, d[j - 1], alpha, d[j], d[j])
                    }
                }
                copy(d[degree], result)
            }
        }
        return result
    }
}

/**
 * @author Kevin Ludwig
 */
class BSplineFloat2(
    degree: Int
) : BSpline<MutableFloat2>(degree, ::MutableFloat2, { source, destination -> destination.set(source) }, { w0, p0, w1, p1, result ->
    result.x = p0.x * w0 + p1.x * w1
    result.y = p0.y * w0 + p1.y * w1
})

/**
 * @author Kevin Ludwig
 */
class BSplineFloat3(
    degree: Int
) : BSpline<MutableFloat3>(degree, ::MutableFloat3, { source, destination -> destination.set(source) }, { w0, p0, w1, p1, result ->
    result.x = p0.x * w0 + p1.x * w1
    result.y = p0.y * w0 + p1.y * w1
    result.z = p0.z * w0 + p1.z * w1
})
