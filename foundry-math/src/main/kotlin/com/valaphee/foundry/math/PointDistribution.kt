/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
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
