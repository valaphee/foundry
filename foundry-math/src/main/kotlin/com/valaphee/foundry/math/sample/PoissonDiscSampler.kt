/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.math.sample

import com.valaphee.foundry.math.Float2
import com.valaphee.foundry.math.Int2
import com.valaphee.foundry.math.defaultRandom
import com.valaphee.foundry.math.noise.Noise
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

/**
 * @author Kevin Ludwig
 */
class PoissonDiscSampler(
    private val minimum: Float2,
    private val maximum: Float2,
    private val count: Int = 30,
    private val minimumDistance: Float,
    private val noise: Noise,
    private val random: Random = defaultRandom
) {
    private val size = Float2(maximum.x - minimum.x, maximum.y - minimum.y)
    private val cellSize = minimumDistance / sqrt(2.0f)
    private val gridWidth = (size.x / cellSize).toInt() + 1
    private val gridHeight = (size.y / cellSize).toInt() + 1

    fun sample(): List<Float2> {
        val activePoints = mutableListOf<Float2>()
        val points = mutableListOf<Float2>()
        val grid = Array(gridWidth) { Array(gridHeight) { mutableListOf<Float2>() } }

        fun addFirstPoint() {
            val point = Float2(maximum.x * random.nextFloat(), maximum.y * random.nextFloat())
            val index = float2ToInt2(point, minimum, cellSize)

            activePoints.add(point)
            points.add(point)
            grid[index.x][index.y].add(point)
        }

        fun addNextPoint(point: Float2): Boolean {
            var found = false
            val fraction = noise[0, point.x, point.y]
            val point2 = pointAround(point, fraction * minimumDistance)

            if (point2.x >= minimum.x && point2.x < maximum.x && point2.y > minimum.y && point2.y < maximum.y) {
                val point2Index = float2ToInt2(point2, minimum, cellSize)
                var tooClose = false
                var i = max(0, point2Index.x - 2)
                while (i < min(gridWidth, point2Index.x + 3) && !tooClose) {
                    var j = max(0, point2Index.y - 2)
                    while (j < min(gridHeight, point2Index.y + 3) && !tooClose) {
                        for (gridPoint in grid[i][j]) {
                            if (gridPoint.distance(point2) < minimumDistance * fraction) {
                                tooClose = true
                            }
                        }
                        j++
                    }
                    i++
                }
                if (!tooClose) {
                    found = true
                    activePoints.add(point2)
                    points.add(point2)
                    grid[point2Index.x][point2Index.y].add(point2)
                }
            }

            return found
        }

        addFirstPoint()
        while (activePoints.isNotEmpty() && points.size < maximumPoints) {
            val pointIndex = random.nextInt(activePoints.size)
            val point = activePoints[pointIndex]
            var found = false
            for (k in 0 until count) found = found or addNextPoint(point)
            if (!found) activePoints.removeAt(pointIndex)
        }

        return points
    }

    private fun float2ToInt2(point: Float2, origin: Float2, cellSize: Float) = Int2(((point.x - origin.x) / cellSize).toInt(), ((point.y - origin.y) / cellSize).toInt())

    private fun pointAround(center: Float2, minimumDistance: Float): Float2 {
        val radius = minimumDistance + minimumDistance * random.nextFloat()
        val angle = 2 * PI.toFloat() * random.nextFloat()
        return Float2(center.x + radius * sin(angle), center.y + radius * cos(angle))
    }

    companion object {
        private const val maximumPoints = 100000
    }
}
