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

import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * @author Kevin Ludwig
 */
open class Float3(
    x: Float,
    y: Float,
    z: Float
) {
    protected val _vector = FloatArray(3).apply {
        this[0] = x
        this[1] = y
        this[2] = z
    }
    open val x get() = this[0]
    open val y get() = this[1]
    open val z get() = this[2]

    constructor(value: Float) : this(value, value, value)

    constructor(value: Float3) : this(value.x, value.y, value.z)

    open operator fun get(index: Int) = _vector[index]

    operator fun component1() = x

    operator fun component2() = y

    operator fun component3() = z

    fun add(value: Float3, result: MutableFloat3) = result.set(this).add(value)

    operator fun plus(value: Float3) = Float3(x + value.x, y + value.y, z + value.z)

    fun sub(value: Float3, result: MutableFloat3) = result.set(this).sub(value)

    operator fun minus(value: Float3) = Float3(x - value.x, y - value.y, z - value.z)

    fun mul(value: Float3, result: MutableFloat3) = result.set(this).mul(value)

    fun dot(value: Float3) = x * value.x + y * value.y + z * value.z

    operator fun times(value: Float3) = dot(value)

    fun scale(value: Float, result: MutableFloat3) = result.set(this).scale(value)

    operator fun times(value: Float) = Float3(x * value, y * value, z * value)

    fun normalize(value: MutableFloat3) = value.set(this).normalize()

    fun rotate(value: Float, axisX: Float, axisY: Float, axisZ: Float, result: MutableFloat3) = result.set(this).rotate(value, axisX, axisY, axisZ)

    fun rotate(value: Float, axis: Float3, result: MutableFloat3) = result.set(this).rotate(value, axis.x, axis.y, axis.z)

    fun mix(value: Float3, weight: Float, result: MutableFloat3): MutableFloat3 {
        result.x = value.x * weight + x * (1.0f - weight)
        result.y = value.y * weight + y * (1.0f - weight)
        result.z = value.z * weight + z * (1.0f - weight)
        return result
    }

    fun planeSpace(p: MutableFloat3, q: MutableFloat3) {
        if (abs(z) > Sqrt1_2) {
            val a = y * y + z * z
            val k = 1.0f / sqrt(a)
            p.x = 0.0f
            p.y = -z * k
            p.z = y * k
            q.x = a * k
            q.y = -x * p.z
            q.z = x * p.y
        } else {
            val a = x * x + y * y
            val k = 1.0f / sqrt(a)
            p.x = -y * k
            p.y = x * k
            p.z = 0.0f
            q.x = -z * p.y
            q.y = z * p.x
            q.z = a * k
        }
    }

    fun cross(value: Float3, result: MutableFloat3): MutableFloat3 {
        result.x = y * value.z - z * value.y
        result.y = z * value.x - x * value.z
        result.z = x * value.y - y * value.x
        return result
    }

    infix fun x(other: Float3) = Float3(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x)

    fun length2() = x * x + y * y + z * z

    fun length() = sqrt(length2())

    fun distance2(other: Float3): Float {
        val dx = x - other.x
        val dy = y - other.y
        val dz = z - other.z
        return dx * dx + dy * dy + dz * dz
    }

    fun distance(other: Float3) = sqrt(distance2(other))

    fun equals(other: Float3, epsilon: Float = FloatEpsilon) = equals(x, other.x, epsilon) && equals(y, other.y, epsilon) && equals(z, other.z, epsilon)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Float3) return false

        if (x != other.x) return false
        if (y != other.y) return false
        if (z != other.z) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        return result
    }

    override fun toString(): String = "($x|$y|$z)"

    fun toInt3() = Int3(x.toInt(), y.toInt(), z.toInt())

    fun toMutableInt3() = toMutableInt3(MutableInt3())

    fun toMutableInt3(result: MutableInt3) = result.set(x.toInt(), y.toInt(), z.toInt())

    fun toMutableFloat3() = toMutableFloat3(MutableFloat3())

    fun toMutableFloat3(result: MutableFloat3) = result.set(x, y, z)

    fun toDouble3(): Double3 = Double3(x.toDouble(), y.toDouble(), z.toDouble())

    fun toMutableDouble3() = toMutableDouble3(MutableDouble3())

    fun toMutableDouble3(result: MutableDouble3) = result.set(x.toDouble(), y.toDouble(), z.toDouble())

    companion object {
        val Zero = Float3(0.0f)
        val XAxis = Float3(1.0f, 0.0f, 0.0f)
        val YAxis = Float3(0.0f, 1.0f, 0.0f)
        val ZAxis = Float3(0.0f, 0.0f, 1.0f)
        val NegativeXAxis = Float3(-1.0f, 0.0f, 0.0f)
        val NegativeYAxis = Float3(0.0f, -1.0f, 0.0f)
        val NegativeZAxis = Float3(0.0f, 0.0f, -1.0f)
    }
}

fun Float3.xy() = Float2(x, y)

/**
 * @author Kevin Ludwig
 */
open class MutableFloat3(
    x: Float,
    y: Float,
    z: Float
) : Float3(x, y, z) {
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
    val vector get() = _vector

    constructor() : this(0.0f, 0.0f, 0.0f)

    constructor(value: Float) : this(value, value, value)

    constructor(value: Float3) : this(value.x, value.y, value.z)

    open operator fun set(index: Int, value: Float) {
        _vector[index] = value
    }

    fun set(x: Float, y: Float, z: Float) = apply {
        this.x = x
        this.y = y
        this.z = z
    }

    fun set(value: Float3) = apply {
        x = value.x
        y = value.y
        z = value.z
    }

    fun add(value: Float3) = apply {
        x += value.x
        y += value.y
        z += value.z
    }

    operator fun plusAssign(value: Float3) {
        add(value)
    }

    fun sub(value: Float3) = apply {
        x -= value.x
        y -= value.y
        z -= value.z
    }

    operator fun minusAssign(value: Float3) {
        sub(value)
    }

    fun mul(value: Float3) = apply {
        x *= value.x
        y *= value.y
        z *= value.z
    }

    fun scale(value: Float) = apply {
        x *= value
        y *= value
        z *= value
    }

    operator fun timesAssign(value: Float) {
        scale(value)
    }

    operator fun divAssign(value: Float) {
        scale(1.0f / value)
    }

    fun normalize() = scale(1.0f / length())

    fun rotate(value: Float, axis: Float3) = rotate(value, axis.x, axis.y, axis.z)

    fun rotate(value: Float, axisX: Float, axisY: Float, axisZ: Float): MutableFloat3 {
        val rad = value.toRad()
        val sin = sin(rad)
        val cos = cos(rad)
        val vers = 1.0f - cos
        val rx = x * (axisX * axisX * vers + cos) + y * (axisX * axisY * vers - axisZ * sin) + z * (axisX * axisZ * vers + axisY * sin)
        val ry = x * (axisY * axisX * vers + axisZ * sin) + y * (axisY * axisY * vers + cos) + z * (axisY * axisZ * vers - axisX * sin)
        val rz = x * (axisX * axisZ * vers - axisY * sin) + y * (axisY * axisZ * vers + axisX * sin) + z * (axisZ * axisZ * vers + cos)
        x = rx
        y = ry
        z = rz
        return this
    }
}

fun add(a: Float3, b: Float3) = a.add(b, MutableFloat3())

fun sub(a: Float3, b: Float3) = a.sub(b, MutableFloat3())

fun scale(a: Float3, b: Float) = a.scale(b, MutableFloat3())

fun normalize(a: Float3) = a.normalize(MutableFloat3())

fun cross(a: Float3, b: Float3) = a.cross(b, MutableFloat3())

fun MutableFloat3.xy(): MutableFloat2 = MutableFloat2(x, y)
