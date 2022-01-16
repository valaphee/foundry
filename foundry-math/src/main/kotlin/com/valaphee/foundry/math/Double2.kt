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

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * @author Kevin Ludwig
 */
open class Double2(
    x: Double,
    y: Double
) {
    protected val _vector = DoubleArray(2).apply {
        this[0] = x
        this[1] = y
    }
    open val x get() = this[0]
    open val y get() = this[1]

    constructor(value: Double) : this(value, value)

    constructor(other: Double2) : this(other.x, other.y)

    open operator fun get(index: Int) = _vector[index]

    operator fun component1() = x

    operator fun component2() = y

    fun add(value: Double2, result: MutableDouble2) = result.set(this).add(value)

    operator fun plus(value: Double2) = Double2(x + value.x, y + value.y)

    fun sub(value: Double2, result: MutableDouble2) = result.set(this).sub(value)

    operator fun minus(value: Double2) = Double2(x - value.x, y - value.y)

    fun mul(value: Double2, result: MutableDouble2) = result.set(this).mul(value)

    fun dot(value: Double2) = x * value.x + y * value.y

    operator fun times(value: Double2) = dot(value)

    fun scale(value: Double, result: MutableDouble2) = result.set(this).scale(value)

    operator fun times(value: Double) = Double2(x * value, y * value)

    fun normalize(result: MutableDouble2) = result.set(this).normalize()

    fun mix(value: Double2, weight: Double, result: MutableDouble2): MutableDouble2 {
        result.x = value.x * weight + x * (1.0 - weight)
        result.y = value.y * weight + y * (1.0 - weight)
        return result
    }

    fun rotate(value: Double, result: MutableDouble2) = result.set(this).rotate(value)

    fun length2() = x * x + y * y

    fun length() = sqrt(length2())

    fun distance2(value: Double2): Double {
        val dx = x - value.x
        val dy = y - value.y
        return dx * dx + dy * dy
    }

    fun distance(other: Double2) = sqrt(distance2(other))

    fun equals(other: Double2, epsilon: Double = DoubleEpsilon) = equals(x, other.x, epsilon) && equals(y, other.y, epsilon)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Double2) return false

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }

    override fun toString() = "($x|$y)"

    fun toInt2() = Int2(x.toInt(), y.toInt())

    fun toMutableInt2() = toMutableInt2(MutableInt2())

    fun toMutableInt2(result: MutableInt2) = result.set(x.toInt(), y.toInt())

    fun toFloat2() = Float2(x.toFloat(), y.toFloat())

    fun toMutableFloat2() = toMutableFloat2(MutableFloat2())

    fun toMutableFloat2(result: MutableFloat2) = result.set(x.toFloat(), y.toFloat())

    fun toMutableDouble2() = toMutableDouble2(MutableDouble2())

    fun toMutableDouble2(result: MutableDouble2) = result.set(x, y)

    companion object {
        val Zero = Double2(0.0)
        val XAxis = Double2(1.0, 0.0)
        val YAxis = Double2(0.0, 1.0)
        val NegativeXAxis = Double2(-1.0, 0.0)
        val NegativeYAxis = Double2(0.0, -1.0)
    }
}

/**
 * @author Kevin Ludwig
 */
open class MutableDouble2(
    x: Double,
    y: Double
) : Double2(x, y) {
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

    constructor() : this(0.0)

    constructor(value: Double) : this(value, value)

    constructor(value: Double2) : this(value.x, value.y)

    open operator fun set(index: Int, value: Double) {
        _vector[index] = value
    }

    fun set(x: Double, y: Double) = apply {
        this.x = x
        this.y = y
    }

    fun set(value: Double2) = apply {
        x = value.x
        y = value.y
    }

    fun add(value: Double2) = apply {
        x += value.x
        y += value.y
    }

    operator fun plusAssign(value: Double2) {
        add(value)
    }

    fun sub(value: Double2) = apply {
        x -= value.x
        y -= value.y
    }

    operator fun minusAssign(value: Double2) {
        sub(value)
    }

    fun mul(value: Double2) = apply {
        x *= value.x
        y *= value.y
    }

    fun scale(value: Double) = apply {
        x *= value
        y *= value
    }

    operator fun timesAssign(value: Double) {
        scale(value)
    }

    operator fun divAssign(value: Double) {
        scale(1.0 / value)
    }

    fun normalize() = scale(1.0 / length())

    fun rotate(value: Double) = apply {
        val rad = value.toRad()
        val sin = sin(rad)
        val cos = cos(rad)
        val rx = x * cos - y * sin
        val ry = x * sin + y * cos
        x = rx
        y = ry
    }
}

fun add(a: Double2, b: Double2) = a.add(b, MutableDouble2())

fun sub(a: Double2, b: Double2) = a.sub(b, MutableDouble2())

fun scale(a: Double2, b: Double) = a.scale(b, MutableDouble2())

fun normalize(a: Double2) = a.normalize(MutableDouble2())
