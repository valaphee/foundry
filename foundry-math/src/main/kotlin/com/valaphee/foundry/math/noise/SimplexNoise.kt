/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.math.noise

import com.valaphee.foundry.math.fastFloor

/**
 * @author Kevin Ludwig
 */
class SimplexNoise : Noise {
    override fun get(seed: Int, x: Float, y: Float): Float {
        var t = (x + y) * f2f
        val i = fastFloor(x + t)
        val j = fastFloor(y + t)
        t = (i + j) * g2f
        val X0 = i - t
        val Y0 = j - t
        val x0 = x - X0
        val y0 = y - Y0
        val i1: Int
        val j1: Int
        if (x0 > y0) {
            i1 = 1
            j1 = 0
        } else {
            i1 = 0
            j1 = 1
        }
        val x1 = x0 - i1 + g2f
        val y1 = y0 - j1 + g2f
        val x2 = x0 - 1.0f + f2f
        val y2 = y0 - 1.0f + f2f
        val n0: Float
        val n1: Float
        val n2: Float
        t = 0.5f - x0 * x0 - y0 * y0
        if (t < 0.0f) n0 = 0.0f else {
            t *= t
            n0 = t * t * gradHash(seed, i, j, x0, y0)
        }
        t = 0.5f - x1 * x1 - y1 * y1
        if (t < 0.0f) n1 = 0.0f else {
            t *= t
            n1 = t * t * gradHash(seed, i + i1, j + j1, x1, y1)
        }
        t = 0.5f - x2 * x2 - y2 * y2
        if (t < 0.0f) n2 = 0.0f else {
            t *= t
            n2 = t * t * gradHash(seed, i + 1, j + 1, x2, y2)
        }
        return 50.0f * (n0 + n1 + n2)
    }

    override fun get(seed: Int, x: Float, y: Float, z: Float): Float {
        var t = (x + y + z) * f3f
        val i = fastFloor(x + t)
        val j = fastFloor(y + t)
        val k = fastFloor(z + t)
        t = (i + j + k) * g3f
        val x0 = x - (i - t)
        val y0 = y - (j - t)
        val z0 = z - (k - t)
        val i1: Int
        val j1: Int
        val k1: Int
        val i2: Int
        val j2: Int
        val k2: Int
        if (x0 >= y0) when {
            y0 >= z0 -> {
                i1 = 1
                j1 = 0
                k1 = 0
                i2 = 1
                j2 = 1
                k2 = 0
            }
            x0 >= z0 -> {
                i1 = 1
                j1 = 0
                k1 = 0
                i2 = 1
                j2 = 0
                k2 = 1
            }
            else -> {
                i1 = 0
                j1 = 0
                k1 = 1
                i2 = 1
                j2 = 0
                k2 = 1
            }
        } else when {
            y0 < z0 -> {
                i1 = 0
                j1 = 0
                k1 = 1
                i2 = 0
                j2 = 1
                k2 = 1
            }
            x0 < z0 -> {
                i1 = 0
                j1 = 1
                k1 = 0
                i2 = 0
                j2 = 1
                k2 = 1
            }
            else -> {
                i1 = 0
                j1 = 1
                k1 = 0
                i2 = 1
                j2 = 1
                k2 = 0
            }
        }
        val x1 = x0 - i1 + g3f
        val y1 = y0 - j1 + g3f
        val z1 = z0 - k1 + g3f
        val x2 = x0 - i2 + f3f
        val y2 = y0 - j2 + f3f
        val z2 = z0 - k2 + f3f
        val x3 = x0 + g33f
        val y3 = y0 + g33f
        val z3 = z0 + g33f
        val n0: Float
        val n1: Float
        val n2: Float
        val n3: Float
        t = 0.6f - x0 * x0 - y0 * y0 - z0 * z0
        if (t < 0.0f) n0 = 0.0f else {
            t *= t
            n0 = t * t * gradHash(seed, i, j, k, x0, y0, z0)
        }
        t = 0.6f - x1 * x1 - y1 * y1 - z1 * z1
        if (t < 0.0f) n1 = 0.0f else {
            t *= t
            n1 = t * t * gradHash(seed, i + i1, j + j1, k + k1, x1, y1, z1)
        }
        t = 0.6f - x2 * x2 - y2 * y2 - z2 * z2
        if (t < 0.0f) n2 = 0.0f else {
            t *= t
            n2 = t * t * gradHash(seed, i + i2, j + j2, k + k2, x2, y2, z2)
        }
        t = 0.6f - x3 * x3 - y3 * y3 - z3 * z3
        if (t < 0.0f) n3 = 0.0f else {
            t *= t
            n3 = t * t * gradHash(seed, i + 1, j + 1, k + 1, x3, y3, z3)
        }
        return 32.0f * (n0 + n1 + n2 + n3)
    }

    companion object {
        private const val f2f = 1.0f / 2.0f
        private const val g2f = 1.0f / 4.0f
        private const val f3f = 1.0f / 3.0f
        private const val g3f = 1.0f / 6.0f
        private const val g33f = g3f * 3.0f - 1.0f
    }
}
