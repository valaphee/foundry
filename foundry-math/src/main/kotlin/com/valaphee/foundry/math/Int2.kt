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
