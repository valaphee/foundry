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
