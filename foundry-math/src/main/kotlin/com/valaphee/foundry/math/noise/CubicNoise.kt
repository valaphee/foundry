/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
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
        val xs = x - x1.toFloat()
        val ys = y - y1.toFloat()
        return cubicLerp(
            cubicLerp(valHash(seed, x0, y0), valHash(seed, x1, y0), valHash(seed, x2, y0), valHash(seed, x3, y0), xs),
            cubicLerp(valHash(seed, x0, y1), valHash(seed, x1, y1), valHash(seed, x2, y1), valHash(seed, x3, y1), xs),
            cubicLerp(valHash(seed, x0, y2), valHash(seed, x1, y2), valHash(seed, x2, y2), valHash(seed, x3, y2), xs),
            cubicLerp(valHash(seed, x0, y3), valHash(seed, x1, y3), valHash(seed, x2, y3), valHash(seed, x3, y3), xs),
            ys
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
        val xs = x - x1.toFloat()
        val ys = y - y1.toFloat()
        val zs = z - z1.toFloat()
        return cubicLerp(
            cubicLerp(
                cubicLerp(valHash(seed, x0, y0, z0), valHash(seed, x1, y0, z0), valHash(seed, x2, y0, z0), valHash(seed, x3, y0, z0), xs),
                cubicLerp(valHash(seed, x0, y1, z0), valHash(seed, x1, y1, z0), valHash(seed, x2, y1, z0), valHash(seed, x3, y1, z0), xs),
                cubicLerp(valHash(seed, x0, y2, z0), valHash(seed, x1, y2, z0), valHash(seed, x2, y2, z0), valHash(seed, x3, y2, z0), xs),
                cubicLerp(valHash(seed, x0, y3, z0), valHash(seed, x1, y3, z0), valHash(seed, x2, y3, z0), valHash(seed, x3, y3, z0), xs),
                ys
            ), cubicLerp(
                cubicLerp(valHash(seed, x0, y0, z1), valHash(seed, x1, y0, z1), valHash(seed, x2, y0, z1), valHash(seed, x3, y0, z1), xs),
                cubicLerp(valHash(seed, x0, y1, z1), valHash(seed, x1, y1, z1), valHash(seed, x2, y1, z1), valHash(seed, x3, y1, z1), xs),
                cubicLerp(valHash(seed, x0, y2, z1), valHash(seed, x1, y2, z1), valHash(seed, x2, y2, z1), valHash(seed, x3, y2, z1), xs),
                cubicLerp(valHash(seed, x0, y3, z1), valHash(seed, x1, y3, z1), valHash(seed, x2, y3, z1), valHash(seed, x3, y3, z1), xs),
                ys
            ), cubicLerp(
                cubicLerp(valHash(seed, x0, y0, z2), valHash(seed, x1, y0, z2), valHash(seed, x2, y0, z2), valHash(seed, x3, y0, z2), xs),
                cubicLerp(valHash(seed, x0, y1, z2), valHash(seed, x1, y1, z2), valHash(seed, x2, y1, z2), valHash(seed, x3, y1, z2), xs),
                cubicLerp(valHash(seed, x0, y2, z2), valHash(seed, x1, y2, z2), valHash(seed, x2, y2, z2), valHash(seed, x3, y2, z2), xs),
                cubicLerp(valHash(seed, x0, y3, z2), valHash(seed, x1, y3, z2), valHash(seed, x2, y3, z2), valHash(seed, x3, y3, z2), xs),
                ys
            ), cubicLerp(
                cubicLerp(valHash(seed, x0, y0, z3), valHash(seed, x1, y0, z3), valHash(seed, x2, y0, z3), valHash(seed, x3, y0, z3), xs),
                cubicLerp(valHash(seed, x0, y1, z3), valHash(seed, x1, y1, z3), valHash(seed, x2, y1, z3), valHash(seed, x3, y1, z3), xs),
                cubicLerp(valHash(seed, x0, y2, z3), valHash(seed, x1, y2, z3), valHash(seed, x2, y2, z3), valHash(seed, x3, y2, z3), xs),
                cubicLerp(valHash(seed, x0, y3, z3), valHash(seed, x1, y3, z3), valHash(seed, x2, y3, z3), valHash(seed, x3, y3, z3), xs),
                ys
            ), zs
        ) * bounding3
    }

    companion object {
        private const val bounding2 = 1 / (1.5f * 1.5f)
        private const val bounding3 = 1 / (1.5f * 1.5f * 1.5f)
    }
}
