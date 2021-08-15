/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.util

import com.valaphee.foundry.math.clamp
import kotlin.math.min

/**
 * @author Kevin Ludwig
 */
class ColorGradient(
    vararg colors: Pair<Float, Color>,
    steps: Int = DefaultSteps
) {
    companion object {
        const val DefaultSteps = 256
    }

    private val gradient = Array(steps) { MutableColor() }

    constructor(vararg colors: Color, steps: Int = DefaultSteps) : this(*Array(colors.size) { it.toFloat() to colors[it] }, steps = steps)

    init {
        require(colors.size >= 2)

        colors.sortBy { it.first }
        val firstColor = colors.first().first
        val lastColor = colors.last().first

        var colorIndex = 0
        var color0 = colors[colorIndex++]
        var color1 = colors[colorIndex++]
        for (i in 0 until steps) {
            var value = i / (steps - 1.0f) * (lastColor - firstColor) + firstColor
            while (value > color1.first) {
                color0 = color1
                color1 = colors[min(colorIndex++, colors.size)]
            }
            value = 1.0f - (value - color0.first) / (color1.first - color0.first)
            gradient[i].set(color0.second).scale(value).add(color1.second, 1.0f - value)
        }
    }

    fun getColor(value: Float, minimum: Float = 0.0f, maximum: Float = 1.0f) = gradient[((value - minimum) / (maximum - minimum) * gradient.size).toInt().clamp(0, gradient.size - 1)]

    fun getInterpolatedColor(value: Float, result: MutableColor, minimum: Float = 0.0f, maximum: Float = 1.0f): MutableColor {
        val value = ((value - minimum) / (maximum - minimum) * gradient.size).clamp(0.0f, gradient.size - 1.0f)
        val lowerIndex = value.toInt().clamp(0, gradient.size - 1)
        val upperIndex = (lowerIndex + 1).clamp(0, gradient.size - 1)
        val upperPart = upperIndex - value
        val lowerPart = 1.0f - upperPart
        val upperColor = gradient[upperIndex]
        result.set(gradient[lowerIndex]).scale(lowerPart)
        result.r += upperColor.r * upperPart
        result.g += upperColor.g * upperPart
        result.b += upperColor.b * upperPart
        result.a += upperColor.a * upperPart
        return result
    }

    fun inverted() = ColorGradient(*Array<Pair<Float, Color>>(gradient.size) { it.toFloat() to gradient[gradient.lastIndex - it] }, steps = gradient.size)
}
