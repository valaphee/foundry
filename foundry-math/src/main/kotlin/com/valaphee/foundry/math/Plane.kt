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

package com.valaphee.foundry.math

import com.valaphee.foundry.math.collision.Ray

/**
 * @author Kevin Ludwig
 */
class Plane() {
    val point = MutableFloat3()
    val normal = MutableFloat3(Float3.YAxis)

    constructor(point: Float3, normal: Float3) : this() {
        this.point.set(point)
        this.normal.set(normal)
    }

    fun intersectionPoint(ray: Ray, result: MutableFloat3): Boolean {
        val denominator = normal.dot(ray.direction)
        if (!denominator.isZero()) {
            val t = point.sub(ray.origin, result).dot(normal) / denominator
            result.set(ray.direction).scale(t).add(ray.origin)
            return t >= 0
        }
        return false
    }

    fun toFloat4() = toFloat4(MutableFloat4())

    fun toFloat4(result: MutableFloat4): MutableFloat4 {
        result.x = normal.x
        result.y = normal.y
        result.z = normal.z
        result.w = normal * point
        return result
    }
}
