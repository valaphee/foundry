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
