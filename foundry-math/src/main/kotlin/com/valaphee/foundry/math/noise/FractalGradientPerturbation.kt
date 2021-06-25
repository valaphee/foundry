/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.math.noise

import com.valaphee.foundry.math.MutableFloat2
import com.valaphee.foundry.math.MutableFloat3

/**
 * @author Kevin Ludwig
 */
open class FractalGradientPerturbation(
    frequency: Float = 0.01f,
    amp: Float = 1.0f / 0.45f,
    interpolationType: InterpolationType = InterpolationType.Quintic,
    private val octaves: Int = 3,
    private val gain: Float = 0.5f,
    private val lacunarity: Float = 2.0f,
) : GradientPerturbation(frequency, amp, interpolationType) {
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

    override fun apply(seed: Int, vector: MutableFloat2) {
        var seed0 = seed
        var amp0 = amp * bounding
        var frequency0 = frequency
        apply(seed0, this.frequency, amp0, vector)
        for (i in 1 until octaves) {
            frequency0 *= lacunarity
            amp0 *= gain
            apply(++seed0, frequency0, amp0, vector)
        }
    }

    override fun apply(seed: Int, vector: MutableFloat3) {
        var seed0 = seed
        var amp0 = amp * bounding
        var frequency0 = frequency
        apply(seed0, this.frequency, amp0, vector)
        for (i in 1 until octaves) {
            frequency0 *= lacunarity
            amp0 *= gain
            apply(++seed0, frequency0, amp0, vector)
        }
    }
}
