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

import kotlin.math.sqrt

/**
 * @author Kevin Ludwig
 */
open class Double4(
    x: Double,
    y: Double,
    z: Double,
    w: Double
) {
    protected val _vector = DoubleArray(4).apply {
        this[0] = x
        this[1] = y
        this[2] = z
        this[3] = w
    }
    open val x get() = this[0]
    open val y get() = this[1]
    open val z get() = this[2]
    open val w get() = this[3]

    constructor(value: Double) : this(value, value, value, value)

    constructor(xyz: Double3, w: Double) : this(xyz.x, xyz.y, xyz.z, w)

    constructor(value: Double4) : this(value.x, value.y, value.z, value.w)

    open operator fun get(index: Int) = _vector[index]

    operator fun component1() = x

    operator fun component2() = y

    operator fun component3() = z

    operator fun component4() = w

    fun add(value: Double4, result: MutableDouble4) = result.set(this).add(value)

    operator fun plus(value: Double4) = Double4(x + value.x, y + value.y, z + value.z, w + value.w)

    fun sub(value: Double4, result: MutableDouble4) = result.set(this).sub(value)

    operator fun minus(value: Double4) = Double4(x - value.x, y - value.y, z - value.z, w - value.w)

    fun mul(value: Double4, result: MutableDouble4) = result.set(this).mul(value)

    fun dot(value: Double4) = x * value.x + y * value.y + z * value.z + w * value.w

    operator fun times(value: Double4) = dot(value)

    fun scale(value: Double, result: MutableDouble4) = result.set(this).scale(value)

    operator fun times(value: Double) = Double4(x * value, y * value, z * value, w * value)

    fun normalize(value: MutableDouble4) = value.set(this).normalize()

    fun mix(value: Double4, weight: Double, result: MutableDouble4): MutableDouble4 {
        result.x = value.x * weight + x * (1.0 - weight)
        result.y = value.y * weight + y * (1.0 - weight)
        result.z = value.z * weight + z * (1.0 - weight)
        result.w = value.w * weight + w * (1.0 - weight)
        return result
    }

    fun product(value: Double4, result: MutableDouble4): MutableDouble4 {
        result.x = w * value.x + x * value.w + y * value.z - z * value.y
        result.y = w * value.y + y * value.w + z * value.x - x * value.z
        result.z = w * value.z + z * value.w + x * value.y - y * value.x
        result.w = w * value.w - x * value.x - y * value.y - z * value.z
        return result
    }

    fun length2() = x * x + y * y + z * z + w * w

    fun length() = sqrt(length2())

    fun distance2(other: Double4): Double {
        val dx = x - other.x
        val dy = y - other.y
        val dz = z - other.z
        val dw = z - other.w
        return dx * dx + dy * dy + dz * dz + dw * dw
    }

    fun distance(value: Double4) = sqrt(distance2(value))

    fun equals(value: Double4, epsilon: Double = DoubleEpsilon) = equals(x, value.x, epsilon) && equals(y, value.y, epsilon) && equals(z, value.z, epsilon) && equals(w, value.w, epsilon)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Double4) return false

        if (x != other.x) return false
        if (y != other.y) return false
        if (z != other.z) return false
        if (w != other.w) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        result = 31 * result + w.hashCode()
        return result
    }

    override fun toString() = "($x|$y|$z|$w)"

    fun toInt4() = Int4(x.toInt(), y.toInt(), z.toInt(), w.toInt())

    fun toMutableInt4() = toMutableInt4(MutableInt4())

    fun toMutableInt4(result: MutableInt4) = result.set(x.toInt(), y.toInt(), z.toInt(), w.toInt())

    fun toFloat4() = Float4(x.toFloat(), y.toFloat(), z.toFloat(), w.toFloat())

    fun toMutableFloat4() = toMutableFloat4(MutableFloat4())

    fun toMutableFloat4(result: MutableFloat4) = result.set(x.toFloat(), y.toFloat(), z.toFloat(), w.toFloat())

    fun toMutableDouble4() = toMutableDouble4(MutableDouble4())

    fun toMutableDouble4(result: MutableDouble4) = result.set(x, y, z, w)

    companion object {
        val Zero = Double4(0.0)
        val XAxis = Double4(1.0, 0.0, 0.0, 0.0)
        val YAxis = Double4(0.0, 1.0, 0.0, 0.0)
        val ZAxis = Double4(0.0, 0.0, 1.0, 0.0)
        val WAxis = Double4(0.0, 0.0, 0.0, 1.0)
        val NegativeXAxis = Double4(-1.0, 0.0, 0.0, 0.0)
        val NegativeYAxis = Double4(0.0, -1.0, 0.0, 0.0)
        val NegativeZAxis = Double4(0.0, 0.0, -1.0, 0.0)
        val NegativeWAxis = Double4(0.0, 0.0, 0.0, -1.0)
    }
}

/**
 * @author Kevin Ludwig
 */
open class MutableDouble4(
    x: Double,
    y: Double,
    z: Double,
    w: Double
) : Double4(x, y, z, w) {
    override var x
        get() = this[0]
        set(value) {
            this[0] = value
        }
    override var y
        get() = this[1]
        set(value) {
            this[1] = value
        }
    override var z
        get() = this[2]
        set(value) {
            this[2] = value
        }
    override var w
        get() = this[3]
        set(value) {
            this[3] = value
        }

    constructor() : this(0.0)

    constructor(value: Double) : this(value, value, value, value)

    constructor(xyz: Double3, w: Double) : this(xyz.x, xyz.y, xyz.z, w)

    constructor(other: Double4) : this(other.x, other.y, other.z, other.w)

    open operator fun set(index: Int, value: Double) {
        _vector[index] = value
    }

    fun set(x: Double, y: Double, z: Double, w: Double) = apply {
        this.x = x
        this.y = y
        this.z = z
        this.w = w
    }

    fun set(xyz: Double3, w: Double = 0.0) = apply {
        x = xyz.x
        y = xyz.y
        z = xyz.z
        this.w = w
    }

    fun set(value: Double4) = apply {
        x = value.x
        y = value.y
        z = value.z
        w = value.w
    }

    fun add(value: Double4) = apply {
        x += value.x
        y += value.y
        z += value.z
        w += value.w
    }

    operator fun plusAssign(value: Double4) {
        add(value)
    }

    fun sub(value: Double4) = apply {
        x -= value.x
        y -= value.y
        z -= value.z
        w -= value.w
    }

    operator fun minusAssign(value: Double4) {
        sub(value)
    }

    fun mul(value: Double4) = apply {
        x *= value.x
        y *= value.y
        z *= value.z
        w *= value.w
    }

    fun scale(value: Double) = apply {
        x *= value
        y *= value
        z *= value
        w *= value
    }

    fun normalize() = scale(1.0f / length())

    fun product(value: Double4) = apply {
        val px = w * value.x + x * value.w + y * value.z - z * value.y
        val py = w * value.y + y * value.w + z * value.x - x * value.z
        val pz = w * value.z + z * value.w + x * value.y - y * value.x
        val pw = w * value.w - x * value.x - y * value.y - z * value.z
        set(px, py, pz, pw)
    }
}

fun add(a: Double4, b: Double4) = a.add(b, MutableDouble4())

fun sub(a: Double4, b: Double4) = a.sub(b, MutableDouble4())

fun scale(a: Double4, b: Double) = a.scale(b, MutableDouble4())
