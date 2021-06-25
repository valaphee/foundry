/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.math.noise

import kotlin.math.abs

/**
 * @author Kevin Ludwig
 */
class FractalNoise(
    private val noise: Noise,
    private val octaves: Int = 3,
    private val gain: Float = 0.5f,
    private val lacunarity: Float = 2.0f,
    private val type: FractalType = FractalType.FBM,
) : Noise {
    private val bounding: Float

    init {
        var amp = gain
        var ampFractal = 1.0f
        for (i in 1 until octaves) {
            ampFractal += amp
            amp *= gain
        }
        bounding = 1.0f / ampFractal
    }

    override fun get(seed: Int, x: Float, y: Float): Float {
        var x0 = x
        var y0 = y
        var seed0 = seed
        var sum: Float
        var amp = 1.0f
        return when (type) {
            FractalType.FBM -> {
                sum = noise[seed0, x0, y0]
                for (i in 1 until octaves) {
                    x0 *= lacunarity
                    y0 *= lacunarity
                    amp *= gain
                    sum += noise[++seed0, x0, y0] * amp
                }
                sum * bounding
            }
            FractalType.Billow -> {
                sum = abs(noise[seed0, x0, y0]) * 2.0f - 1.0f
                for (i in 1 until octaves) {
                    x0 *= lacunarity
                    y0 *= lacunarity
                    amp *= gain
                    sum += (abs(noise[++seed0, x0, y0]) * 2.0f - 1.0f) * amp
                }
                sum * bounding
            }
            FractalType.RigidMulti -> {
                sum = 1.0f - abs(noise[seed0, x0, y0])
                for (i in 1 until octaves) {
                    x0 *= lacunarity
                    y0 *= lacunarity
                    amp *= gain
                    sum -= (1.0f - abs(noise[++seed0, x0, y0])) * amp
                }
                sum
            }
        }
    }

    override fun get(seed: Int,  x: Float, y: Float, z: Float): Float {
        var x0 = x
        var y0 = y
        var z0 = z
        var seed0 = seed
        var sum: Float
        var amp = 1.0f
        return when (type) {
            FractalType.FBM -> {
                sum = noise[seed0, x0, y0, z0]
                for (i in 1 until octaves) {
                    x0 *= lacunarity
                    y0 *= lacunarity
                    z0 *= lacunarity
                    amp *= gain
                    sum += noise[++seed0, x0, y0, z0] * amp
                }
                sum * bounding
            }
            FractalType.Billow -> {
                sum = abs(noise[seed0, x0, y0, z0]) * 2.0f - 1.0f
                for (i in 1 until octaves) {
                    x0 *= lacunarity
                    y0 *= lacunarity
                    z0 *= lacunarity
                    amp *= gain
                    sum += (abs(noise[++seed0, x0, y0, z0]) * 2.0f - 1.0f) * amp
                }
                sum * bounding
            }
            FractalType.RigidMulti -> {
                sum = 1.0f - abs(noise[seed0, x0, y0, z0])
                for (i in 1 until octaves) {
                    x0 *= lacunarity
                    y0 *= lacunarity
                    z0 *= lacunarity
                    amp *= gain
                    sum -= (1 - abs(noise[seed0, x0, y0, z0])) * amp
                }
                sum
            }
        }
    }
}
