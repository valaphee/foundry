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

package com.valaphee.foundry.math.noise

import com.valaphee.foundry.math.fastFloor
import com.valaphee.foundry.math.interpolateHermite
import com.valaphee.foundry.math.interpolateQuintic
import com.valaphee.foundry.math.lerp

/**
 * @author Kevin Ludwig
 */
class PerlinNoise(
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
        val xd0 = x - x0
        val yd0 = y - y0
        val xd1 = xd0 - 1
        val yd1 = yd0 - 1
        val xr0 = lerp(gradHash(seed, x0, y0, xd0, yd0), gradHash(seed, x1, y0, xd1, yd0), xr)
        val xr1 = lerp(gradHash(seed, x0, y1, xd0, yd1), gradHash(seed, x1, y1, xd1, yd1), xr)
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
        val xd0 = x - x0
        val yd0 = y - y0
        val zd0 = z - z0
        val xd1 = xd0 - 1
        val yd1 = yd0 - 1
        val zd1 = zd0 - 1
        val xr00 = lerp(gradHash(seed, x0, y0, z0, xd0, yd0, zd0), gradHash(seed, x1, y0, z0, xd1, yd0, zd0), xr)
        val xr10 = lerp(gradHash(seed, x0, y1, z0, xd0, yd1, zd0), gradHash(seed, x1, y1, z0, xd1, yd1, zd0), xr)
        val xr01 = lerp(gradHash(seed, x0, y0, z1, xd0, yd0, zd1), gradHash(seed, x1, y0, z1, xd1, yd0, zd1), xr)
        val xr11 = lerp(gradHash(seed, x0, y1, z1, xd0, yd1, zd1), gradHash(seed, x1, y1, z1, xd1, yd1, zd1), xr)
        val yr0 = lerp(xr00, xr10, yr)
        val yr1 = lerp(xr01, xr11, yr)
        return lerp(yr0, yr1, zr)
    }
}
