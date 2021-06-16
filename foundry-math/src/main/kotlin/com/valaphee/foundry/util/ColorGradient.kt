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
        val beginColor = colors.first().first
        val endColor = colors.last().first

        var colorIndex = 0
        var colorA = colors[colorIndex++]
        var colorB = colors[colorIndex++]
        for (i in 0 until steps) {
            var between = i / (steps - 1.0f) * (endColor - beginColor) + beginColor
            while (between > colorB.first) {
                colorA = colorB
                colorB = colors[min(colorIndex++, colors.size)]
            }
            between = 1.0f - (between - colorA.first) / (colorB.first - colorA.first)
            gradient[i].set(colorA.second).scale(between).add(colorB.second, 1.0f - between)
        }
    }

    fun getColor(value: Float, minimum: Float = 0.0f, maximum: Float = 1.0f) = gradient[((value - minimum) / (maximum - minimum) * gradient.size).toInt().clamp(0, gradient.size - 1)]

    fun getColorInterpolated(value: Float, result: MutableColor, minimum: Float = 0.0f, maximum: Float = 1.0f): MutableColor {
        val between = ((value - minimum) / (maximum - minimum) * gradient.size).clamp(0.0f, gradient.size - 1.0f)
        val lowerIndex = between.toInt().clamp(0, gradient.size - 1)
        val upperIndex = (lowerIndex + 1).clamp(0, gradient.size - 1)
        val upperPart = upperIndex - between
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
