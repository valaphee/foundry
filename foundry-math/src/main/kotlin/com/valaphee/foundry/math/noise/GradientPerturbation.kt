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
