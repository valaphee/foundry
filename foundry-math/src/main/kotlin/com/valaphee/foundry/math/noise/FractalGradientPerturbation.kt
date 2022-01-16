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
