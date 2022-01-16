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
import com.valaphee.foundry.math.fastFloor
import com.valaphee.foundry.math.interpolateHermite
import com.valaphee.foundry.math.interpolateQuintic
import com.valaphee.foundry.math.lerp
import com.valaphee.foundry.math.noise.CellularNoise.Companion.cell2
import com.valaphee.foundry.math.noise.CellularNoise.Companion.cell3

/**
 * @author Kevin Ludwig
 */
open class GradientPerturbation(
    protected val frequency: Float = 0.01f,
    protected val amp: Float = 1.0f / 0.45f,
    private val interpolationType: InterpolationType = InterpolationType.Quintic,
) {
    open fun apply(seed: Int, vector: MutableFloat2) = apply(seed, frequency, amp, vector)

    fun apply(seed: Int, frequency: Float, amp: Float, vector: MutableFloat2) {
        val x = vector.x * frequency
        val y = vector.y * frequency
        val x0 = fastFloor(x)
        val y0 = fastFloor(y)
        val x1 = x0 + 1
        val y1 = y0 + 1
        val xr: Float
        val yr: Float
        when (interpolationType) {
            InterpolationType.Linear -> {
                xr = x - x0
                yr = y - y0
            }
            InterpolationType.Hermite -> {
                xr = interpolateHermite(x - x0)
                yr = interpolateHermite(y - y0)
            }
            InterpolationType.Quintic -> {
                xr = interpolateQuintic(x - x0)
                yr = interpolateQuintic(y - y0)
            }
        }
        var cell0 = cell2[hash(seed, x0, y0) and 255]
        var cell1 = cell2[hash(seed, x1, y0) and 255]
        val xx0 = lerp(cell0.x, cell1.x, xr)
        val yy0 = lerp(cell0.y, cell1.y, xr)
        cell0 = cell2[hash(seed, x0, y1) and 255]
        cell1 = cell2[hash(seed, x1, y1) and 255]
        val xx1 = lerp(cell0.x, cell1.x, xr)
        val yy1 = lerp(cell0.y, cell1.y, xr)
        vector.x += lerp(xx0, xx1, yr) * amp
        vector.y += lerp(yy0, yy1, yr) * amp
    }

    open fun apply(seed: Int, vector: MutableFloat3) = apply(seed, frequency, amp, vector)

    fun apply(seed: Int, frequency: Float, amp: Float, vector: MutableFloat3) {
        val x = vector.x * frequency
        val y = vector.y * frequency
        val z = vector.z * frequency
        val x0 = fastFloor(x)
        val y0 = fastFloor(y)
        val z0 = fastFloor(z)
        val x1 = x0 + 1
        val y1 = y0 + 1
        val z1 = z0 + 1
        val xr: Float
        val yr: Float
        val zr: Float
        when (interpolationType) {
            InterpolationType.Linear -> {
                xr = x - x0
                yr = y - y0
                zr = z - z0
            }
            InterpolationType.Hermite -> {
                xr = interpolateHermite(x - x0)
                yr = interpolateHermite(y - y0)
                zr = interpolateHermite(z - z0)
            }
            InterpolationType.Quintic -> {
                xr = interpolateQuintic(x - x0)
                yr = interpolateQuintic(y - y0)
                zr = interpolateQuintic(z - z0)
            }
        }
        var cell0 = cell3[hash(seed, x0, y0, z0) and 255]
        var cell1 = cell3[hash(seed, x1, y0, z0) and 255]
        var xx0 = lerp(cell0.x, cell1.x, xr)
        var yy0 = lerp(cell0.y, cell1.y, xr)
        var zz0 = lerp(cell0.z, cell1.z, xr)
        cell0 = cell3[hash(seed, x0, y1, z0) and 255]
        cell1 = cell3[hash(seed, x1, y1, z0) and 255]
        var xx2 = lerp(cell0.x, cell1.x, xr)
        var yy2 = lerp(cell0.y, cell1.y, xr)
        var zz2 = lerp(cell0.z, cell1.z, xr)
        val xx1 = lerp(xx0, xx2, yr)
        val yy1 = lerp(yy0, yy2, yr)
        val zz1 = lerp(zz0, zz2, yr)
        cell0 = cell3[hash(seed, x0, y0, z1) and 255]
        cell1 = cell3[hash(seed, x1, y0, z1) and 255]
        xx0 = lerp(cell0.x, cell1.x, xr)
        yy0 = lerp(cell0.y, cell1.y, xr)
        zz0 = lerp(cell0.z, cell1.z, xr)
        cell0 = cell3[hash(seed, x0, y1, z1) and 255]
        cell1 = cell3[hash(seed, x1, y1, z1) and 255]
        xx2 = lerp(cell0.x, cell1.x, xr)
        yy2 = lerp(cell0.y, cell1.y, xr)
        zz2 = lerp(cell0.z, cell1.z, xr)
        vector.x += lerp(xx1, lerp(xx0, xx2, yr), zr) * amp
        vector.y += lerp(yy1, lerp(yy0, yy2, yr), zr) * amp
        vector.z += lerp(zz1, lerp(zz0, zz2, yr), zr) * amp
    }
}
