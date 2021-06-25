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
        var x1 = x
        var y1 = y
        var _seed = seed
        var sum: Float
        var amp = 1.0f
        return when (type) {
            FractalType.FBM -> {
                sum = noise[_seed, x1, y1]
                for (i in 1 until octaves) {
                    x1 *= lacunarity
                    y1 *= lacunarity
                    amp *= gain
                    sum += noise[++_seed, x1, y1] * amp
                }
                sum * bounding
            }
            FractalType.Billow -> {
                sum = abs(noise[_seed, x1, y1]) * 2.0f - 1.0f
                for (i in 1 until octaves) {
                    x1 *= lacunarity
                    y1 *= lacunarity
                    amp *= gain
                    sum += (abs(noise[++_seed, x1, y1]) * 2.0f - 1.0f) * amp
                }
                sum * bounding
            }
            FractalType.RigidMulti -> {
                sum = 1.0f - abs(noise[_seed, x1, y1])
                for (i in 1 until octaves) {
                    x1 *= lacunarity
                    y1 *= lacunarity
                    amp *= gain
                    sum -= (1.0f - abs(noise[++_seed, x1, y1])) * amp
                }
                sum
            }
        }
    }

    override fun get(seed: Int,  x: Float, y: Float, z: Float): Float {
        var x1 = x
        var y1 = y
        var z1 = z
        var _seed = seed
        var sum: Float
        var amp = 1.0f
        return when (type) {
            FractalType.FBM -> {
                sum = noise[_seed, x1, y1, z1]
                for (i in 1 until octaves) {
                    x1 *= lacunarity
                    y1 *= lacunarity
                    z1 *= lacunarity
                    amp *= gain
                    sum += noise[++_seed, x1, y1, z1] * amp
                }
                sum * bounding
            }
            FractalType.Billow -> {
                sum = abs(noise[_seed, x1, y1, z1]) * 2.0f - 1.0f
                for (i in 1 until octaves) {
                    x1 *= lacunarity
                    y1 *= lacunarity
                    z1 *= lacunarity
                    amp *= gain
                    sum += (abs(noise[++_seed, x1, y1, z1]) * 2.0f - 1.0f) * amp
                }
                sum * bounding
            }
            FractalType.RigidMulti -> {
                sum = 1.0f - abs(noise[_seed, x1, y1, z1])
                for (i in 1 until octaves) {
                    x1 *= lacunarity
                    y1 *= lacunarity
                    z1 *= lacunarity
                    amp *= gain
                    sum -= (1 - abs(noise[_seed, x1, y1, z1])) * amp
                }
                sum
            }
        }
    }
}
