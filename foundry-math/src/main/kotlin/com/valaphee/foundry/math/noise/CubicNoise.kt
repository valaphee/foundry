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
