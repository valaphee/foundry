/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.math

/**
 * @author Kevin Ludwig
 */
open class Int3(
    x: Int,
    y: Int,
    z: Int
) {
    protected val _vector = IntArray(3).apply {
        this[0] = x
        this[1] = y
        this[2] = z
    }
    open val x get() = this[0]
    open val y get() = this[1]
    open val z get() = this[2]

    constructor(value: Int) : this(value, value, value)

    constructor(value: Int3) : this(value.x, value.y, value.z)

    open operator fun get(index: Int) = _vector[index]

    operator fun component1() = x

    operator fun component2() = y

    operator fun component3() = z

    fun add(value: Int3, result: MutableInt3) = result.set(this).add(value)

    operator fun plus(value: Int3) = Int3(x + value.x, y + value.y, z + value.z)

    fun sub(value: Int3, result: MutableInt3) = result.set(this).sub(value)

    operator fun minus(value: Int3) = Int3(x - value.x, y - value.y, z - value.z)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Int3) return false

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

    fun toMutableInt3() = toMutableInt3(MutableInt3())

    fun toMutableInt3(result: MutableInt3) = result.set(x, y, z)

    fun toFloat3() = Float3(x.toFloat(), y.toFloat(), z.toFloat())

    fun toMutableFloat3() = toMutableFloat3(MutableFloat3())

    fun toMutableFloat3(result: MutableFloat3) = result.set(x.toFloat(), y.toFloat(), z.toFloat())

    fun toDouble3() = Double3(x.toDouble(), y.toDouble(), z.toDouble())

    fun toMutableDouble3() = toMutableDouble3(MutableDouble3())

    fun toMutableDouble3(result: MutableDouble3) = result.set(x.toDouble(), y.toDouble(), z.toDouble())

    companion object {
        val Zero = Int3(0)
        val XAxis = Int3(1, 0, 0)
        val YAxis = Int3(0, 1, 0)
        val ZAxis = Int3(0, 0, 1)
        val NegativeXAxis = Int3(-1, 0, 0)
        val NegativeYAxis = Int3(0, -1, 0)
        val NegativeZAxis = Int3(0, 0, -1)
    }
}

/**
 * @author Kevin Ludwig
 */
open class MutableInt3(
    x: Int,
    y: Int,
    z: Int
) : Int3(x, y, z) {
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
    val array get() = _vector

    constructor() : this(0, 0, 0)

    constructor(value: Int) : this(value, value, value)

    constructor(value: Int3) : this(value.x, value.y, value.z)

    open operator fun set(index: Int, value: Int) {
        _vector[index] = value
    }

    fun set(x: Int, y: Int, z: Int) = apply {
        this.x = x
        this.y = y
        this.z = z
    }

    fun set(value: Int3) = apply {
        x = value.x
        y = value.y
        z = value.z
    }

    fun add(value: Int3) = apply {
        x += value.x
        y += value.y
        z += value.z
    }

    operator fun plusAssign(value: Int3) {
        add(value)
    }

    fun sub(value: Int3) = apply {
        x -= value.x
        y -= value.y
        z -= value.z
    }

    operator fun minusAssign(value: Int3) {
        sub(value)
    }
}
