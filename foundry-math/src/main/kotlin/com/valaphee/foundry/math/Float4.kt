/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.math

import kotlin.math.sqrt

/**
 * @author Kevin Ludwig
 */
open class Float4(
    x: Float,
    y: Float,
    z: Float,
    w: Float
) {
    protected val _vector = FloatArray(4).apply {
        this[0] = x
        this[1] = y
        this[2] = z
        this[3] = w
    }
    open val x get() = this[0]
    open val y get() = this[1]
    open val z get() = this[2]
    open val w get() = this[3]

    constructor(value: Float) : this(value, value, value, value)

    constructor(xyz: Float3, w: Float) : this(xyz.x, xyz.y, xyz.z, w)

    constructor(value: Float4) : this(value.x, value.y, value.z, value.w)

    open operator fun get(index: Int) = _vector[index]

    operator fun component1() = x

    operator fun component2() = y

    operator fun component3() = z

    operator fun component4() = w

    fun add(value: Float4, result: MutableFloat4) = result.set(this).add(value)

    operator fun plus(value: Float4) = Float4(x + value.x, y + value.y, z + value.z, w + value.w)

    fun sub(value: Float4, result: MutableFloat4) = result.set(this).sub(value)

    operator fun minus(value: Float4) = Float4(x - value.x, y - value.y, z - value.z, w - value.w)

    fun mul(value: Float4, result: MutableFloat4) = result.set(this).mul(value)

    fun dot(value: Float4) = x * value.x + y * value.y + z * value.z + w * value.w

    operator fun times(value: Float4) = dot(value)

    fun scale(value: Float, result: MutableFloat4) = result.set(this).scale(value)

    operator fun times(value: Float) = Float4(x * value, y * value, z * value, w * value)

    fun normalize(result: MutableFloat4) = result.set(this).normalize()

    fun mix(value: Float4, weight: Float, result: MutableFloat4): MutableFloat4 {
        result.x = value.x * weight + x * (1.0f - weight)
        result.y = value.y * weight + y * (1.0f - weight)
        result.z = value.z * weight + z * (1.0f - weight)
        result.w = value.w * weight + w * (1.0f - weight)
        return result
    }

    fun product(value: Float4, result: MutableFloat4): MutableFloat4 {
        result.x = w * value.x + x * value.w + y * value.z - z * value.y
        result.y = w * value.y + y * value.w + z * value.x - x * value.z
        result.z = w * value.z + z * value.w + x * value.y - y * value.x
        result.w = w * value.w - x * value.x - y * value.y - z * value.z
        return result
    }

    fun length2() = x * x + y * y + z * z + w * w

    fun length() = sqrt(length2())

    fun distance2(other: Float4): Float {
        val dx = x - other.x
        val dy = y - other.y
        val dz = z - other.z
        val dw = z - other.w
        return dx * dx + dy * dy + dz * dz + dw * dw
    }

    fun distance(other: Float4) = sqrt(distance2(other))

    fun equals(other: Float4, epsilon: Float = FloatEpsilon) = equals(x, other.x, epsilon) && equals(y, other.y, epsilon) && equals(z, other.z, epsilon) && equals(w, other.w, epsilon)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Float4) return false

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

    fun toMutableFloat4() = toMutableFloat4(MutableFloat4())

    fun toMutableFloat4(result: MutableFloat4) = result.set(x, y, z, w)

    fun toDouble4() = Double4(x.toDouble(), y.toDouble(), z.toDouble(), w.toDouble())

    fun toMutableDouble4() = toMutableDouble4(MutableDouble4())

    fun toMutableDouble4(result: MutableDouble4) = result.set(x.toDouble(), y.toDouble(), z.toDouble(), w.toDouble())

    fun getXyz(result: MutableFloat3): MutableFloat3 {
        result.x = x
        result.y = y
        result.z = z
        return result
    }

    companion object {
        val Zero = Float4(0.0f)
        val XAxis = Float4(1.0f, 0.0f, 0.0f, 0.0f)
        val YAxis = Float4(0.0f, 1.0f, 0.0f, 0.0f)
        val ZAxis = Float4(0.0f, 0.0f, 1.0f, 0.0f)
        val WAxis = Float4(0.0f, 0.0f, 0.0f, 1.0f)
        val NegativeXAxis = Float4(-1.0f, 0.0f, 0.0f, 0.0f)
        val NegativeYAxis = Float4(0.0f, -1.0f, 0.0f, 0.0f)
        val NegativeZAxis = Float4(0.0f, 0.0f, -1.0f, 0.0f)
        val NegativeWAxis = Float4(0.0f, 0.0f, 0.0f, -1.0f)
    }
}

/**
 * @author Kevin Ludwig
 */
open class MutableFloat4(
    x: Float,
    y: Float,
    z: Float,
    w: Float
) : Float4(x, y, z, w) {
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
    val vector get() = _vector

    constructor() : this(0.0f)

    constructor(value: Float) : this(value, value, value, value)

    constructor(xyz: Float3, w: Float) : this(xyz.x, xyz.y, xyz.z, w)

    constructor(value: Float4) : this(value.x, value.y, value.z, value.w)

    open operator fun set(index: Int, value: Float) {
        _vector[index] = value
    }

    fun set(x: Float, y: Float, z: Float, w: Float) = apply {
        this.x = x
        this.y = y
        this.z = z
        this.w = w
    }

    fun set(xyz: Float3, w: Float = 0.0f) = apply {
        x = xyz.x
        y = xyz.y
        z = xyz.z
        this.w = w
    }

    fun set(value: Float4) = apply {
        x = value.x
        y = value.y
        z = value.z
        w = value.w
    }

    fun add(value: Float4) = apply {
        x += value.x
        y += value.y
        z += value.z
        w += value.w
    }

    operator fun plusAssign(value: Float4) {
        add(value)
    }

    fun sub(value: Float4) = apply {
        x -= value.x
        y -= value.y
        z -= value.z
        w -= value.w
    }

    operator fun minusAssign(value: Float4) {
        sub(value)
    }

    fun mul(value: Float4) = apply {
        x *= value.x
        y *= value.y
        z *= value.z
        w *= value.w
    }

    fun scale(value: Float) = apply {
        x *= value
        y *= value
        z *= value
        w *= value
    }

    fun normalize() = scale(1 / length())

    fun product(value: Float4) = apply {
        val px = w * value.x + x * value.w + y * value.z - z * value.y
        val py = w * value.y + y * value.w + z * value.x - x * value.z
        val pz = w * value.z + z * value.w + x * value.y - y * value.x
        val pw = w * value.w - x * value.x - y * value.y - z * value.z
        set(px, py, pz, pw)
    }
}

fun add(a: Float4, b: Float4) = a.add(b, MutableFloat4())

fun sub(a: Float4, b: Float4) = a.sub(b, MutableFloat4())

fun scale(a: Float4, b: Float) = a.scale(b, MutableFloat4())
