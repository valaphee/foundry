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

import com.valaphee.foundry.math.cubicLerp
import com.valaphee.foundry.math.fastFloor

/**
 * @author Kevin Ludwig
 */
class CubicNoise : Noise {
    override fun get(seed: Int, x: Float, y: Float): Float {
        val x1 = fastFloor(x)
        val y1 = fastFloor(y)
        val x0 = x1 - 1
        val y0 = y1 - 1
        val x2 = x1 + 1
        val y2 = y1 + 1
        val x3 = x1 + 2
        val y3 = y1 + 2
        val xd = x - x1.toFloat()
        val yd = y - y1.toFloat()
        return cubicLerp(
            cubicLerp(valHash(seed, x0, y0), valHash(seed, x1, y0), valHash(seed, x2, y0), valHash(seed, x3, y0), xd),
            cubicLerp(valHash(seed, x0, y1), valHash(seed, x1, y1), valHash(seed, x2, y1), valHash(seed, x3, y1), xd),
            cubicLerp(valHash(seed, x0, y2), valHash(seed, x1, y2), valHash(seed, x2, y2), valHash(seed, x3, y2), xd),
            cubicLerp(valHash(seed, x0, y3), valHash(seed, x1, y3), valHash(seed, x2, y3), valHash(seed, x3, y3), xd),
            yd
        ) * bounding2
    }

    override fun get(seed: Int, x: Float, y: Float, z: Float): Float {
        val x1 = fastFloor(x)
        val y1 = fastFloor(y)
        val z1 = fastFloor(z)
        val x0 = x1 - 1
        val y0 = y1 - 1
        val z0 = z1 - 1
        val x2 = x1 + 1
        val y2 = y1 + 1
        val z2 = z1 + 1
        val x3 = x1 + 2
        val y3 = y1 + 2
        val z3 = z1 + 2
        val xd = x - x1.toFloat()
        val yd = y - y1.toFloat()
        val zd = z - z1.toFloat()
        return cubicLerp(
            cubicLerp(
                cubicLerp(valHash(seed, x0, y0, z0), valHash(seed, x1, y0, z0), valHash(seed, x2, y0, z0), valHash(seed, x3, y0, z0), xd),
                cubicLerp(valHash(seed, x0, y1, z0), valHash(seed, x1, y1, z0), valHash(seed, x2, y1, z0), valHash(seed, x3, y1, z0), xd),
                cubicLerp(valHash(seed, x0, y2, z0), valHash(seed, x1, y2, z0), valHash(seed, x2, y2, z0), valHash(seed, x3, y2, z0), xd),
                cubicLerp(valHash(seed, x0, y3, z0), valHash(seed, x1, y3, z0), valHash(seed, x2, y3, z0), valHash(seed, x3, y3, z0), xd),
                yd
            ), cubicLerp(
                cubicLerp(valHash(seed, x0, y0, z1), valHash(seed, x1, y0, z1), valHash(seed, x2, y0, z1), valHash(seed, x3, y0, z1), xd),
                cubicLerp(valHash(seed, x0, y1, z1), valHash(seed, x1, y1, z1), valHash(seed, x2, y1, z1), valHash(seed, x3, y1, z1), xd),
                cubicLerp(valHash(seed, x0, y2, z1), valHash(seed, x1, y2, z1), valHash(seed, x2, y2, z1), valHash(seed, x3, y2, z1), xd),
                cubicLerp(valHash(seed, x0, y3, z1), valHash(seed, x1, y3, z1), valHash(seed, x2, y3, z1), valHash(seed, x3, y3, z1), xd),
                yd
            ), cubicLerp(
                cubicLerp(valHash(seed, x0, y0, z2), valHash(seed, x1, y0, z2), valHash(seed, x2, y0, z2), valHash(seed, x3, y0, z2), xd),
                cubicLerp(valHash(seed, x0, y1, z2), valHash(seed, x1, y1, z2), valHash(seed, x2, y1, z2), valHash(seed, x3, y1, z2), xd),
                cubicLerp(valHash(seed, x0, y2, z2), valHash(seed, x1, y2, z2), valHash(seed, x2, y2, z2), valHash(seed, x3, y2, z2), xd),
                cubicLerp(valHash(seed, x0, y3, z2), valHash(seed, x1, y3, z2), valHash(seed, x2, y3, z2), valHash(seed, x3, y3, z2), xd),
                yd
            ), cubicLerp(
                cubicLerp(valHash(seed, x0, y0, z3), valHash(seed, x1, y0, z3), valHash(seed, x2, y0, z3), valHash(seed, x3, y0, z3), xd),
                cubicLerp(valHash(seed, x0, y1, z3), valHash(seed, x1, y1, z3), valHash(seed, x2, y1, z3), valHash(seed, x3, y1, z3), xd),
                cubicLerp(valHash(seed, x0, y2, z3), valHash(seed, x1, y2, z3), valHash(seed, x2, y2, z3), valHash(seed, x3, y2, z3), xd),
                cubicLerp(valHash(seed, x0, y3, z3), valHash(seed, x1, y3, z3), valHash(seed, x2, y3, z3), valHash(seed, x3, y3, z3), xd),
                yd
            ), zd
        ) * bounding3
    }

    companion object {
        private const val bounding2 = 1.0f / (1.5f * 1.5f)
        private const val bounding3 = 1.0f / (1.5f * 1.5f * 1.5f)
    }
}
