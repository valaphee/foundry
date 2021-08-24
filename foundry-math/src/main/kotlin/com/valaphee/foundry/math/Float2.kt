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

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * @author Kevin Ludwig
 */
open class Float2(
    x: Float,
    y: Float
) {
    protected val _vector = FloatArray(2).apply {
        this[0] = x
        this[1] = y
    }
    open val x get() = this[0]
    open val y get() = this[1]

    constructor(value: Float) : this(value, value)

    constructor(value: Float2) : this(value.x, value.y)

    open operator fun get(index: Int) = _vector[index]

    operator fun component1() = x

    operator fun component2() = y

    fun add(value: Float2, result: MutableFloat2) = result.set(this).add(value)

    operator fun plus(value: Float2) = Float2(x + value.x, y + value.y)

    fun sub(value: Float2, result: MutableFloat2) = result.set(this).sub(value)

    operator fun minus(value: Float2) = Float2(x - value.x, y - value.y)

    fun mul(value: Float2, result: MutableFloat2) = result.set(this).mul(value)

    fun dot(value: Float2) = x * value.x + y * value.y

    operator fun times(value: Float2) = dot(value)

    fun scale(value: Float, result: MutableFloat2) = result.set(this).scale(value)

    operator fun times(value: Float) = Float2(x * value, y * value)

    fun normalize(result: MutableFloat2) = result.set(this).normalize()

    fun mix(value: Float2, weight: Float, result: MutableFloat2): MutableFloat2 {
        result.x = value.x * weight + x * (1.0f - weight)
        result.y = value.y * weight + y * (1.0f - weight)
        return result
    }

    fun rotate(value: Float, result: MutableFloat2) = result.set(this).rotate(value)

    fun length2() = x * x + y * y

    fun length() = sqrt(length2())

    fun distance2(other: Float2): Float {
        val dx = x - other.x
        val dy = y - other.y
        return dx * dx + dy * dy
    }

    fun distance(other: Float2) = sqrt(distance2(other))

    fun equals(other: Float2, epsilon: Float = FloatEpsilon) = equals(x, other.x, epsilon) && equals(y, other.y, epsilon)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Float2) return false

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }

    override fun toString(): String = "($x|$y)"

    fun toInt2() = Int2(x.toInt(), y.toInt())

    fun toMutableInt2() = toMutableInt2(MutableInt2())

    fun toMutableInt2(result: MutableInt2) = result.set(x.toInt(), y.toInt())

    fun toMutableFloat2() = toMutableFloat2(MutableFloat2())

    fun toMutableFloat2(result: MutableFloat2) = result.set(x, y)

    fun toDouble2() = Double2(x.toDouble(), y.toDouble())

    fun toMutableDouble2() = toMutableDouble2(MutableDouble2())

    fun toMutableDouble2(result: MutableDouble2) = result.set(x.toDouble(), y.toDouble())

    companion object {
        val Zero = Float2(0.0f)
        val XAxis = Float2(1.0f, 0.0f)
        val YAxis = Float2(0.0f, 1.0f)
        val NegativeXAxis = Float2(-1.0f, 0.0f)
        val NegativeYAxis = Float2(0.0f, -1.0f)
    }
}

/**
 * @author Kevin Ludwig
 */
open class MutableFloat2(
    x: Float,
    y: Float
) : Float2(x, y) {
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
    val vector get() = _vector

    constructor() : this(0.0f, 0.0f)

    constructor(value: Float) : this(value, value)

    constructor(value: Float2) : this(value.x, value.y)

    open operator fun set(index: Int, value: Float) {
        _vector[index] = value
    }

    fun set(x: Float, y: Float) = apply {
        this.x = x
        this.y = y
    }

    fun set(value: Float2) = apply {
        x = value.x
        y = value.y
    }

    fun add(value: Float2) = apply {
        x += value.x
        y += value.y
    }

    operator fun plusAssign(value: Float2) {
        add(value)
    }

    fun sub(value: Float2) = apply {
        x -= value.x
        y -= value.y
    }

    operator fun minusAssign(value: Float2) {
        sub(value)
    }

    fun mul(value: Float2) = apply {
        x *= value.x
        y *= value.y
    }

    fun scale(value: Float) = apply {
        x *= value
        y *= value
    }

    operator fun timesAssign(factor: Float) {
        scale(factor)
    }

    operator fun divAssign(div: Float) {
        scale(1 / div)
    }

    fun normalize() = scale(1 / length())

    fun rotate(value: Float) = apply {
        val rad = value.toRad()
        val sin = sin(rad)
        val cos = cos(rad)
        val rx = x * cos - y * sin
        val ry = x * sin + y * cos
        x = rx
        y = ry
    }
}

fun add(a: Float2, b: Float2) = a.add(b, MutableFloat2())

fun sub(a: Float2, b: Float2) = a.sub(b, MutableFloat2())

fun scale(a: Float2, b: Float) = a.scale(b, MutableFloat2())

fun normalize(a: Float2) = a.normalize(MutableFloat2())
