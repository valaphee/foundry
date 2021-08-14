/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.math.noise

/**
 * @author Kevin Ludwig
 */
class BinaryNoise(
    private val noise: Noise,
    private val low: Float = -1.0f,
    private val high: Float = 1.0f,
    private val threshold: Float = 0.0f
) : Noise {
    override fun get(seed: Int, x: Float, y: Float) = if (noise[seed, x, y] > threshold) high else low

    override fun get(seed: Int, x: Float, y: Float, z: Float) = if (noise[seed, x, y, z] > threshold) high else low
}
