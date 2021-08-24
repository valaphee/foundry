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

/**
 * @author Kevin Ludwig
 */
open class Int2(
    x: Int,
    y: Int
) {
    protected val _vector = IntArray(2).apply {
        this[0] = x
        this[1] = y
    }
    open val x get() = this[0]
    open val y get() = this[1]

    constructor(value: Int) : this(value, value)

    constructor(value: Int2) : this(value.x, value.y)

    open operator fun get(index: Int) = _vector[index]

    operator fun component1() = x

    operator fun component2() = y

    fun add(value: Int2, result: MutableInt2) = result.set(this).add(value)

    operator fun plus(value: Int2) = Int2(x + value.x, y + value.y)

    fun sub(value: Int2, result: MutableInt2) = result.set(this).sub(value)

    operator fun minus(value: Int2) = Int2(x - value.x, y - value.y)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Int2) return false

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

    fun toMutableInt2() = toMutableInt2(MutableInt2())

    fun toMutableInt2(result: MutableInt2) = result.set(x, y)

    fun toFloat2() = Float2(x.toFloat(), y.toFloat())

    fun toMutableFloat2() = toMutableFloat2(MutableFloat2())

    fun toMutableFloat2(result: MutableFloat2) = result.set(x.toFloat(), y.toFloat())

    fun toDouble2() = Double2(x.toDouble(), y.toDouble())

    fun toMutableDouble2() = toMutableDouble2(MutableDouble2())

    fun toMutableDouble2(result: MutableDouble2) = result.set(x.toDouble(), y.toDouble())

    companion object {
        val Zero = Int2(0)
        val XAxis = Int2(1, 0)
        val YAxis = Int2(0, 1)
        val NegativeXAxis = Int2(-1, 0)
        val NegativeYAxis = Int2(0, -1)
    }
}

/**
 * @author Kevin Ludwig
 */
open class MutableInt2(
    x: Int,
    y: Int
) : Int2(x, y) {
    override var x
        get() = this[0]
        set(value) {
            _vector[0] = value
        }
    override var y
        get() = this[1]
        set(value) {
            _vector[1] = value
        }
    val vector get() = _vector

    constructor() : this(0)

    constructor(value: Int) : this(value, value)

    constructor(value: Int2) : this(value.x, value.y)

    open operator fun set(index: Int, value: Int) {
        _vector[index] = value
    }

    fun set(value: Int2) = apply {
        x = value.x
        y = value.y
    }

    fun add(value: Int2) = apply {
        x += value.x
        y += value.y
    }

    operator fun plusAssign(value: Int2) {
        add(value)
    }

    fun sub(value: Int2) = apply {
        x -= value.x
        y -= value.y
    }

    operator fun minusAssign(value: Int2) {
        sub(value)
    }
}
