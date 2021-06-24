/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.math.noise

import com.valaphee.foundry.math.fastFloor
import com.valaphee.foundry.math.interpolateHermite
import com.valaphee.foundry.math.interpolateQuintic
import com.valaphee.foundry.math.lerp

/**
 * @author Kevin Ludwig
 */
class ValueNoise(
    private val interpolationType: InterpolationType
) : Noise {
    override fun get(seed: Int, x: Float, y: Float): Float {
        val x0 = fastFloor(x)
        val y0 = fastFloor(y)
        val x1 = x0 + 1
        val y1 = y0 + 1
        val xs: Float
        val ys: Float
        when (interpolationType) {
            InterpolationType.Linear -> {
                xs = x - x0
                ys = y - y0
            }
            InterpolationType.Hermite -> {
                xs = interpolateHermite(x - x0)
                ys = interpolateHermite(y - y0)
            }
            InterpolationType.Quintic -> {
                xs = interpolateQuintic(x - x0)
                ys = interpolateQuintic(y - y0)
            }
        }
        val xf0 = lerp(valHash(seed, x0, y0), valHash(seed, x1, y0), xs)
        val xf1 = lerp(valHash(seed, x0, y1), valHash(seed, x1, y1), xs)
        return lerp(xf0, xf1, ys)
    }

    override fun get(seed: Int, x: Float, y: Float, z: Float): Float {
        val x0 = fastFloor(x)
        val y0 = fastFloor(y)
        val z0 = fastFloor(z)
        val x1 = x0 + 1
        val y1 = y0 + 1
        val z1 = z0 + 1
        val xs: Float
        val ys: Float
        val zs: Float
        when (interpolationType) {
            InterpolationType.Linear -> {
                xs = x - x0
                ys = y - y0
                zs = z - z0
            }
            InterpolationType.Hermite -> {
                xs = interpolateHermite(x - x0)
                ys = interpolateHermite(y - y0)
                zs = interpolateHermite(z - z0)
            }
            InterpolationType.Quintic -> {
                xs = interpolateQuintic(x - x0)
                ys = interpolateQuintic(y - y0)
                zs = interpolateQuintic(z - z0)
            }
        }
        val xf00 = lerp(valHash(seed, x0, y0, z0), valHash(seed, x1, y0, z0), xs)
        val xf10 = lerp(valHash(seed, x0, y1, z0), valHash(seed, x1, y1, z0), xs)
        val xf01 = lerp(valHash(seed, x0, y0, z1), valHash(seed, x1, y0, z1), xs)
        val xf11 = lerp(valHash(seed, x0, y1, z1), valHash(seed, x1, y1, z1), xs)
        val yf0 = lerp(xf00, xf10, ys)
        val yf1 = lerp(xf01, xf11, ys)
        return lerp(yf0, yf1, zs)
    }
}
