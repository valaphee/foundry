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
