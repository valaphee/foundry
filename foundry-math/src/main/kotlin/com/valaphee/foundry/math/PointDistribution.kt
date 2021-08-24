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

import kotlin.random.Random

/**
 * @author Kevin Ludwig
 */
abstract class PointDistribution {
    abstract fun nextPoint(): Float3

    open fun nextPoints(count: Int): List<Float3> {
        val points = mutableListOf<Float3>()
        for (i in 1..count) points += nextPoint()
        return points
    }
}

/**
 * @author Kevin Ludwig
 */
class CubicPointDistribution(
    val size: Float = 1.0f,
    val center: Float3 = Float3.Zero,
    val random: Random = defaultRandom
) : PointDistribution() {
    private val halfSize = size * 0.5f

    override fun nextPoint() = Float3(center.x + random.nextFloat(-halfSize, halfSize), center.y + random.nextFloat(-halfSize, halfSize), center.z + random.nextFloat(-halfSize, halfSize))
}

/**
 * @author Kevin Ludwig
 */
class SphericalPointDistribution(
    val radius: Float = 1.0f,
    val center: Float3 = Float3.Zero,
    val random: Random = defaultRandom
) : PointDistribution() {
    private val radius2 = radius * radius

    override fun nextPoint(): Float3 {
        while (true) {
            val x = random.nextFloat(-radius, radius)
            val y = random.nextFloat(-radius, radius)
            val z = random.nextFloat(-radius, radius)
            if (x * x + y * y + z * z < radius2) return Float3(center.x + x, center.y + y, center.z + z)
        }
    }
}
