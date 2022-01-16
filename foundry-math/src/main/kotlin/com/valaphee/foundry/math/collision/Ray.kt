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
