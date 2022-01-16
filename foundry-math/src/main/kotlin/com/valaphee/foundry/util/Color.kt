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

package com.valaphee.foundry.util

import com.valaphee.foundry.math.Float4
import kotlin.math.pow

/**
 * @author Kevin Ludwig
 */
open class Color(
    r: Float,
    g: Float,
    b: Float,
    a: Float = 1.0f
) : Float4(r, g, b, a) {
    constructor(other: Color) : this(other.r, other.g, other.b, other.a)

    open val r get() = this[0]
    open val g get() = this[1]
    open val b get() = this[2]
    open val a get() = this[3]

    val brightness get() = 0.299f * r + 0.587f * g + 0.114f * b

    fun mix(value: Color, weight: Float) = mix(value, weight, MutableColor())

    fun mix(value: Color, weight: Float, result: MutableColor): MutableColor {
        result.r = value.r * weight + r * (1.0f - weight)
        result.g = value.g * weight + g * (1.0f - weight)
        result.b = value.b * weight + b * (1.0f - weight)
        result.a = value.a * weight + a * (1.0f - weight)
        return result
    }

    fun gamma(value: Float) = gamma(value, MutableColor())

    fun gamma(value: Float, result: MutableColor) = result.set(r.pow(value), g.pow(value), b.pow(value), a)

    fun withAlpha(value: Float) = MutableColor(r, g, b, value)

    fun toLinear() = gamma(2.2f)

    fun toLinear(result: MutableColor) = gamma(2.2f, result)

    fun toSrgb() = gamma(1.0f / 2.2f)

    fun toSrgb(result: MutableColor) = gamma(1.0f / 2.2f, result)

    override fun toString() = if (a == 1.0f) String.format("#%02x%02x%02x", (r * 255.0f).toInt(), (g * 255.0f).toInt(), (b * 255.0f).toInt()) else String.format("#%02x%02x%02x%02x", (r * 255.0f).toInt(), (g * 255.0f).toInt(), (b * 255.0f).toInt(), (a * 255.0f).toInt())

    companion object {
        val Black = Color(0.00f, 0.00f, 0.00f, 1.00f)
        val DarkGray = Color(0.25f, 0.25f, 0.25f, 1.00f)
        val Gray = Color(0.50f, 0.50f, 0.50f, 1.00f)
        val LightGray = Color(0.75f, 0.75f, 0.75f, 1.00f)
        val White = Color(1.00f, 1.00f, 1.00f, 1.00f)
        val Red = Color(1.0f, 0.0f, 0.0f, 1.0f)
        val Green = Color(0.0f, 1.0f, 0.0f, 1.0f)
        val Blue = Color(0.0f, 0.0f, 1.0f, 1.0f)
        val Yellow = Color(1.0f, 1.0f, 0.0f, 1.0f)
        val Cyan = Color(0.0f, 1.0f, 1.0f, 1.0f)
        val Magenta = Color(1.0f, 0.0f, 1.0f, 1.0f)
        val Orange = Color(1.0f, 0.5f, 0.0f, 1.0f)
        val Lime = Color(0.7f, 1.0f, 0.0f, 1.0f)
        val LightRed = Color(1.0f, 0.5f, 0.5f, 1.0f)
        val LightGreen = Color(0.5f, 1.0f, 0.5f, 1.0f)
        val LightBlue = Color(0.5f, 0.5f, 1.0f, 1.0f)
        val LightYellow = Color(1.0f, 1.0f, 0.5f, 1.0f)
        val LightCyan = Color(0.5f, 1.0f, 1.0f, 1.0f)
        val LightMagenta = Color(1.0f, 0.5f, 1.0f, 1.0f)
        val LightOrange = Color(1.0f, 0.75f, 0.5f, 1.0f)
        val DarkRed = Color(0.5f, 0.0f, 0.0f, 1.0f)
        val DarkGreen = Color(0.0f, 0.5f, 0.0f, 1.0f)
        val DarkBlue = Color(0.0f, 0.0f, 0.5f, 1.0f)
        val DarkYellow = Color(0.5f, 0.5f, 0.0f, 1.0f)
        val DarkCyan = Color(0.0f, 0.5f, 0.5f, 1.0f)
        val DarkMagenta = Color(0.5f, 0.0f, 0.5f, 1.0f)
        val DarkOrange = Color(0.5f, 0.25f, 0.0f, 1.0f)

        fun fromHsv(h: Float, s: Float, v: Float, a: Float) = MutableColor().setHsv(h, s, v, a)

        fun fromHex(hex: String): Color {
            if (hex.isEmpty()) return Black

            var hexVar = hex
            if (hexVar[0] == '#') hexVar = hexVar.substring(1)

            var r = 0.0f
            var g = 0.0f
            var b = 0.0f
            var a = 1.0f
            when (hexVar.length) {
                3 -> {
                    val r4 = hexVar.substring(0, 1).toInt(16)
                    val g4 = hexVar.substring(1, 2).toInt(16)
                    val b4 = hexVar.substring(2, 3).toInt(16)
                    r = (r4 or (r4 shl 4)) / 255.0f
                    g = (g4 or (g4 shl 4)) / 255.0f
                    b = (b4 or (b4 shl 4)) / 255.0f
                }
                4 -> {
                    val r4 = hexVar.substring(0, 1).toInt(16)
                    val g4 = hexVar.substring(1, 2).toInt(16)
                    val b4 = hexVar.substring(2, 3).toInt(16)
                    val a4 = hexVar.substring(2, 3).toInt(16)
                    r = (r4 or (r4 shl 4)) / 255.0f
                    g = (g4 or (g4 shl 4)) / 255.0f
                    b = (b4 or (b4 shl 4)) / 255.0f
                    a = (a4 or (a4 shl 4)) / 255.0f
                }
                6 -> {
                    r = hexVar.substring(0, 2).toInt(16) / 255.0f
                    g = hexVar.substring(2, 4).toInt(16) / 255.0f
                    b = hexVar.substring(4, 6).toInt(16) / 255.0f
                }
                8 -> {
                    r = hexVar.substring(0, 2).toInt(16) / 255.0f
                    g = hexVar.substring(2, 4).toInt(16) / 255.0f
                    b = hexVar.substring(4, 6).toInt(16) / 255.0f
                    a = hexVar.substring(6, 8).toInt(16) / 255.0f
                }
            }
            return Color(r, g, b, a)
        }
    }
}

/**
 * @author Kevin Ludwig
 */
open class MutableColor(
    r: Float,
    g: Float,
    b: Float,
    a: Float
) : Color(r, g, b, a) {
    override var r
        get() = this[0]
        set(value) {
            this[0] = value
        }
    override var g
        get() = this[1]
        set(value) {
            this[1] = value
        }
    override var b
        get() = this[2]
        set(value) {
            this[2] = value
        }
    override var a
        get() = this[3]
        set(value) {
            this[3] = value
        }

    val vector get() = _vector

    constructor() : this(0.0f, 0.0f, 0.0f, 1.0f)

    constructor(value: Color) : this(value.r, value.g, value.b, value.a)

    open operator fun set(index: Int, value: Float) {
        _vector[index] = value
    }

    fun set(r: Float, g: Float, b: Float, a: Float) = apply {
        this.r = r
        this.g = g
        this.b = b
        this.a = a
    }

    fun set(other: Float4) = apply {
        r = other.x
        g = other.y
        b = other.z
        a = other.w
    }

    fun setHsv(hue: Float, saturation: Float, value: Float, alpha: Float) = apply {
        var hueVar = hue % 360.0f
        if (hueVar < 0.0f) hueVar += 360.0f
        val hi = (hueVar / 60.0f).toInt()
        val f = hueVar / 60.0f - hi
        val p = value * (1.0f - saturation)
        val q = value * (1.0f - saturation * f)
        val t = value * (1.0f - saturation * (1 - f))
        when (hi) {
            1 -> set(q, value, p, alpha)
            2 -> set(p, value, t, alpha)
            3 -> set(p, q, value, alpha)
            4 -> set(t, p, value, alpha)
            5 -> set(value, p, q, alpha)
            else -> set(value, t, p, alpha)
        }
    }

    fun add(value: Float4) = apply {
        r += value.x
        g += value.y
        b += value.z
        a += value.w
    }

    fun add(value: Float4, weight: Float) = apply {
        r += value.x * weight
        g += value.y * weight
        b += value.z * weight
        a += value.w * weight
    }

    fun sub(value: Float4) = apply {
        r -= value.x
        g -= value.y
        b -= value.z
        a -= value.w
    }

    fun scale(value: Float) = apply {
        r *= value
        g *= value
        b *= value
        a *= value
    }

    fun scaleRgb(value: Float) = apply {
        r *= value
        g *= value
        b *= value
    }

    fun clear() = apply { set(0.0f, 0.0f, 0.0f, 0.0f) }
}
