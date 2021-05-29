/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.math

import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * @author Kevin Ludwig
 */
open class Double3(
    x: Double,
    y: Double,
    z: Double
) {
    protected val _vector = DoubleArray(3).apply {
        this[0] = x
        this[1] = y
        this[2] = z
    }
    open val x get() = this[0]
    open val y get() = this[1]
    open val z get() = this[2]

    constructor(value: Double) : this(value, value, value)

    constructor(value: Double3) : this(value.x, value.y, value.z)

    open operator fun get(index: Int) = _vector[index]

    operator fun component1() = x

    operator fun component2() = y

    operator fun component3() = z

    fun add(value: Double3, result: MutableDouble3) = result.set(this).add(value)

    operator fun plus(value: Double3) = Double3(x + value.x, y + value.y, z + value.z)

    fun sub(value: Double3, result: MutableDouble3) = result.set(this).sub(value)

    operator fun minus(value: Double3) = Double3(x - value.x, y - value.y, z - value.z)

    fun mul(value: Double3, result: MutableDouble3) = result.set(this).mul(value)

    fun dot(value: Double3) = x * value.x + y * value.y + z * value.z

    operator fun times(value: Double3) = dot(value)

    fun scale(value: Double, result: MutableDouble3) = result.set(this).scale(value)

    operator fun times(value: Double) = Double3(x * value, y * value, z * value)

    fun normalize(result: MutableDouble3) = result.set(this).normalize()

    fun mix(value: Double3, weight: Double, result: MutableDouble3): MutableDouble3 {
        result.x = value.x * weight + x * (1.0 - weight)
        result.y = value.y * weight + y * (1.0 - weight)
        result.z = value.z * weight + z * (1.0 - weight)
        return result
    }

    fun rotate(value: Double, axis: Double3, result: MutableDouble3) = result.set(this).rotate(value, axis.x, axis.y, axis.z)

    fun rotate(value: Double, axisX: Double, axisY: Double, axisZ: Double, result: MutableDouble3) = result.set(this).rotate(value, axisX, axisY, axisZ)

    fun planeSpace(p: MutableDouble3, q: MutableDouble3) {
        if (abs(z) > Sqrt1_2) {
            val a = y * y + z * z
            val k = 1.0 / sqrt(a)
            p.x = 0.0
            p.y = -z * k
            p.z = y * k
            q.x = a * k
            q.y = -x * p.z
            q.z = x * p.y
        } else {
            val a = x * x + y * y
            val k = 1.0 / sqrt(a)
            p.x = -y * k
            p.y = x * k
            p.z = 0.0
            q.x = -z * p.y
            q.y = z * p.x
            q.z = a * k
        }
    }

    fun cross(other: Double3, result: MutableDouble3): MutableDouble3 {
        result.x = y * other.z - z * other.y
        result.y = z * other.x - x * other.z
        result.z = x * other.y - y * other.x
        return result
    }

    infix fun x(other: Double3) = Double3(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x)

    fun length2() = x * x + y * y + z * z

    fun length() = sqrt(length2())

    fun distance2(other: Double3): Double {
        val dx = x - other.x
        val dy = y - other.y
        val dz = z - other.z
        return dx * dx + dy * dy + dz * dz
    }

    fun distance(other: Double3) = sqrt(distance2(other))

    fun equals(other: Double3, epsilon: Double = DoubleEpsilon) = equals(x, other.x, epsilon) && equals(y, other.y, epsilon) && equals(z, other.z, epsilon)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Double3) return false

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

    override fun toString() = "($x|$y|$z)"

    fun toInt3() = Int3(x.toInt(), y.toInt(), z.toInt())

    fun toMutableInt3() = toMutableInt3(MutableInt3())

    fun toMutableInt3(result: MutableInt3) = result.set(x.toInt(), y.toInt(), z.toInt())

    fun toFloat3() = Float3(x.toFloat(), y.toFloat(), z.toFloat())

    fun toMutableFloat3() = toMutableFloat3(MutableFloat3())

    fun toMutableFloat3(result: MutableFloat3) = result.set(x.toFloat(), y.toFloat(), z.toFloat())

    fun toMutableDouble3() = toMutableDouble3(MutableDouble3())

    fun toMutableDouble3(result: MutableDouble3) = result.set(x, y, z)

    companion object {
        val Zero = Double3(0.0)
        val XAxis = Double3(1.0, 0.0, 0.0)
        val YAxis = Double3(0.0, 1.0, 0.0)
        val ZAxis = Double3(0.0, 0.0, 1.0)
        val NegativeXAxis = Double3(-1.0, 0.0, 0.0)
        val NegativeYAxis = Double3(0.0, -1.0, 0.0)
        val NegativeZAxis = Double3(0.0, 0.0, -1.0)
    }
}

/**
 * @author Kevin Ludwig
 */
open class MutableDouble3(
    x: Double,
    y: Double,
    z: Double
) : Double3(x, y, z) {
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

    constructor() : this(0.0)

    constructor(value: Double) : this(value, value, value)

    constructor(value: Double3) : this(value.x, value.y, value.z)

    open operator fun set(index: Int, value: Double) {
        _vector[index] = value
    }

    fun set(x: Double, y: Double, z: Double) = apply {
        this.x = x
        this.y = y
        this.z = z
    }

    fun set(value: Double3) = apply {
        x = value.x
        y = value.y
        z = value.z
    }

    fun add(value: Double3) = apply {
        x += value.x
        y += value.y
        z += value.z
    }

    operator fun plusAssign(value: Double3) {
        add(value)
    }

    fun sub(value: Double3) = apply {
        x -= value.x
        y -= value.y
        z -= value.z
    }

    operator fun minusAssign(value: Double3) {
        sub(value)
    }

    fun mul(value: Double3) = apply {
        x *= value.x
        y *= value.y
        z *= value.z
    }

    fun scale(value: Double) = apply {
        x *= value
        y *= value
        z *= value
    }

    operator fun timesAssign(value: Double) {
        scale(value)
    }

    operator fun divAssign(value: Double) {
        scale(1.0 / value)
    }

    fun normalize() = scale(1.0 / length())

    fun rotate(value: Double, axis: Double3) = rotate(value, axis.x, axis.y, axis.z)

    fun rotate(value: Double, axisX: Double, axisY: Double, axisZ: Double) = apply {
        val rad = value.toRad()
        val sin = sin(rad)
        val cos = cos(rad)
        val vers = 1.0 - cos
        val rx = x * (axisX * axisX * vers + cos) + y * (axisX * axisY * vers - axisZ * sin) + z * (axisX * axisZ * vers + axisY * sin)
        val ry = x * (axisY * axisX * vers + axisZ * sin) + y * (axisY * axisY * vers + cos) + z * (axisY * axisZ * vers - axisX * sin)
        val rz = x * (axisX * axisZ * vers - axisY * sin) + y * (axisY * axisZ * vers + axisX * sin) + z * (axisZ * axisZ * vers + cos)
        x = rx
        y = ry
        z = rz
    }
}

fun add(a: Double3, b: Double3) = a.add(b, MutableDouble3())

fun sub(a: Double3, b: Double3) = a.sub(b, MutableDouble3())

fun scale(a: Double3, b: Double) = a.scale(b, MutableDouble3())

fun normalize(a: Double3) = a.normalize(MutableDouble3())

fun cross(a: Double3, b: Double3) = a.cross(b, MutableDouble3())
