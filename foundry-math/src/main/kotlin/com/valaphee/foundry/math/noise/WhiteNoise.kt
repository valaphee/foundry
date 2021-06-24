/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.math.noise

/**
 * @author Kevin Ludwig
 */
class WhiteNoise: Noise {
    override fun get(seed: Int, x: Float, y: Float) = valHash(seed, hash(x), hash(y))

    override fun get(seed: Int, x: Float, y: Float, z: Float) = valHash(seed, hash(x), hash(y), hash(z))

    private fun hash(value: Float): Int {
        val hash = java.lang.Float.floatToRawIntBits(value)
        return hash xor (hash shr 16)
    }
}
