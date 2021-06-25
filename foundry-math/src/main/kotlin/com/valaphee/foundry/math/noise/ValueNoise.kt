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
    private val interpolationType: InterpolationType = InterpolationType.Quintic
) : Noise {
    override fun get(seed: Int, x: Float, y: Float): Float {
        val x0 = fastFloor(x)
        val y0 = fastFloor(y)
        val x1 = x0 + 1
        val y1 = y0 + 1
        val xr: Float
        val yr: Float
        when (interpolationType) {
            InterpolationType.Linear -> {
                xr = x - x0
                yr = y - y0
            }
            InterpolationType.Hermite -> {
                xr = interpolateHermite(x - x0)
                yr = interpolateHermite(y - y0)
            }
            InterpolationType.Quintic -> {
                xr = interpolateQuintic(x - x0)
                yr = interpolateQuintic(y - y0)
            }
        }
        val xr0 = lerp(valHash(seed, x0, y0), valHash(seed, x1, y0), xr)
        val xr1 = lerp(valHash(seed, x0, y1), valHash(seed, x1, y1), xr)
        return lerp(xr0, xr1, yr)
    }

    override fun get(seed: Int, x: Float, y: Float, z: Float): Float {
        val x0 = fastFloor(x)
        val y0 = fastFloor(y)
        val z0 = fastFloor(z)
        val x1 = x0 + 1
        val y1 = y0 + 1
        val z1 = z0 + 1
        val xr: Float
        val yr: Float
        val zr: Float
        when (interpolationType) {
            InterpolationType.Linear -> {
                xr = x - x0
                yr = y - y0
                zr = z - z0
            }
            InterpolationType.Hermite -> {
                xr = interpolateHermite(x - x0)
                yr = interpolateHermite(y - y0)
                zr = interpolateHermite(z - z0)
            }
            InterpolationType.Quintic -> {
                xr = interpolateQuintic(x - x0)
                yr = interpolateQuintic(y - y0)
                zr = interpolateQuintic(z - z0)
            }
        }
        val xr00 = lerp(valHash(seed, x0, y0, z0), valHash(seed, x1, y0, z0), xr)
        val xr10 = lerp(valHash(seed, x0, y1, z0), valHash(seed, x1, y1, z0), xr)
        val xr01 = lerp(valHash(seed, x0, y0, z1), valHash(seed, x1, y0, z1), xr)
        val xr11 = lerp(valHash(seed, x0, y1, z1), valHash(seed, x1, y1, z1), xr)
        val yr0 = lerp(xr00, xr10, yr)
        val yr1 = lerp(xr01, xr11, yr)
        return lerp(yr0, yr1, zr)
    }
}
