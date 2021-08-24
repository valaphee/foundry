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

package com.valaphee.foundry.math.collision

import com.valaphee.foundry.math.Double4x4
import com.valaphee.foundry.math.Float3
import com.valaphee.foundry.math.Float4x4
import com.valaphee.foundry.math.MutableFloat3
import com.valaphee.foundry.math.distancePointToRay2
import com.valaphee.foundry.math.distanceToRay
import com.valaphee.foundry.math.distanceToRay2
import kotlin.math.sqrt

/**
 * @author Kevin Ludwig
 */
class Ray {
    val origin = MutableFloat3()
    val direction = MutableFloat3()

    fun set(value: Ray) {
        origin.set(value.origin)
        direction.set(value.direction)
    }

    fun setFromLookAt(origin: Float3, lookAt: Float3) {
        this.origin.set(origin)
        direction.set(lookAt).sub(origin).normalize()
    }

    fun distanceToPoint(point: Float3): Float = point.distanceToRay(origin, direction)

    fun distanceToPoint2(point: Float3): Float = point.distanceToRay2(origin, direction)

    fun distanceToPoint2(x: Float, y: Float, z: Float) = distancePointToRay2(x, y, z, origin, direction)

    fun sphereIntersection(center: Float3, radius: Float, result: MutableFloat3): Boolean {
        result.set(origin).sub(center)
        val a = direction * direction
        val b = result * direction * 2
        val c = result * result - radius * radius
        val discriminant = b * b - 4 * a * c

        if (discriminant < 0) return false

        val numerator = -b - sqrt(discriminant)
        if (numerator > 0) {
            val d = numerator / (2 * a)
            result.set(direction).scale(d).add(origin)
            return true
        }

        val numerator2 = -b + sqrt(discriminant)
        if (numerator2 > 0) {
            val d = numerator2 / (2 * a)
            result.set(direction).scale(d).add(origin)
            return true
        }

        return false
    }

    fun transformBy(matrix: Float4x4) {
        matrix.transform(origin)
        matrix.transform(direction, 0.0f).normalize()
    }

    fun transformBy(matrix: Double4x4) {
        matrix.transform(origin)
        matrix.transform(direction, 0.0f).normalize()
    }
}
