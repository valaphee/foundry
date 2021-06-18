/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.math

/**
 * @author Kevin Ludwig
 */
open class Int4(x: Int, y: Int, z: Int, w: Int) {
    protected val _vector = IntArray(4).apply {
        this[0] = x
        this[1] = y
        this[2] = z
        this[3] = w
    }
    open val x get() = this[0]
    open val y get() = this[1]
    open val z get() = this[2]
    open val w get() = this[3]

    constructor(value: Int) : this(value, value, value, value)

    constructor(value: Int4) : this(value.x, value.y, value.z, value.w)

    open operator fun get(index: Int) = _vector[index]

    operator fun component1() = x

    operator fun component2() = y

    operator fun component3() = z

    operator fun component4() = w

    fun add(value: Int4, result: MutableInt4) = result.set(this).add(value)

    operator fun plus(value: Int4) = Int4(x + value.x, y + value.y, z + value.z, w - value.w)

    fun sub(value: Int4, result: MutableInt4) = result.set(this).sub(value)

    operator fun minus(value: Int4) = Int4(x - value.x, y - value.y, z - value.z, w - value.w)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Int4) return false

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

    fun toMutableInt4() = toMutableInt4(MutableInt4())

    fun toMutableInt4(result: MutableInt4) = result.set(x, y, z, w)

    fun toFloat4() = Float4(x.toFloat(), y.toFloat(), z.toFloat(), w.toFloat())

    fun toMutableFloat4() = toMutableFloat4(MutableFloat4())

    fun toMutableFloat4(result: MutableFloat4) = result.set(x.toFloat(), y.toFloat(), z.toFloat(), w.toFloat())

    fun toDouble4() = Double4(x.toDouble(), y.toDouble(), z.toDouble(), w.toDouble())

    fun toMutableDouble4() = toMutableDouble4(MutableDouble4())

    fun toMutableDouble4(result: MutableDouble4) = result.set(x.toDouble(), y.toDouble(), z.toDouble(), w.toDouble())

    companion object {
        val Zero = Int4(0)
        val XAxis = Int4(1, 0, 0, 0)
        val YAxis = Int4(0, 1, 0, 0)
        val ZAxis = Int4(0, 0, 1, 0)
        val WAxis = Int4(0, 0, 0, 1)
        val NegativeXAxis = Int4(-1, 0, 0, 0)
        val NegativeYAxis = Int4(0, -1, 0, 0)
        val NegativeZAxis = Int4(0, 0, -1, 0)
        val NegativeWAxis = Int4(0, 0, 0, -1)
    }
}

/**
 * @author Kevin Ludwig
 */
open class MutableInt4(
    x: Int,
    y: Int,
    z: Int,
    w: Int
) : Int4(x, y, z, w) {
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

    constructor() : this(0)

    constructor(value: Int) : this(value, value, value, value)

    constructor(value: Int4) : this(value.x, value.y, value.z, value.w)

    open operator fun set(index: Int, value: Int) {
        _vector[index] = value
    }

    fun set(x: Int, y: Int, z: Int, w: Int) = apply {
        this.x = x
        this.y = y
        this.z = z
        this.w = w
    }

    fun set(value: Int4) = apply {
        x = value.x
        y = value.y
        z = value.z
        w = value.w
    }

    fun add(value: Int4) = apply {
        x += value.x
        y += value.y
        z += value.z
        w += value.w
    }

    operator fun plusAssign(value: Int4) {
        add(value)
    }

    fun sub(value: Int4) = apply {
        x -= value.x
        y -= value.y
        z -= value.z
        w -= value.w
    }

    operator fun minusAssign(value: Int4) {
        sub(value)
    }
}
