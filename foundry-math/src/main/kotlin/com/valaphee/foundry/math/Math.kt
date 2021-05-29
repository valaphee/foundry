/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

@file:Suppress("NOTHING_TO_INLINE")

package com.valaphee.foundry.math

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sqrt

const val Deg2Rad = PI / 180.0

const val Rad2Deg = 180.0 / PI

inline fun Float.toDeg() = this * Rad2Deg.toFloat()

inline fun Float.toRad() = this * Deg2Rad.toFloat()

inline fun Double.toDeg() = this * Rad2Deg

inline fun Double.toRad() = this * Deg2Rad

const val FloatEpsilon = 1e-5f

const val DoubleEpsilon = 1e-10

inline fun equals(a: Float, b: Float, epsilon: Float = FloatEpsilon) = (a - b).isZero(epsilon)

inline fun equals(a: Double, b: Double, epsilon: Double = DoubleEpsilon) = (a - b).isZero(epsilon)

inline fun Float.isZero(epsilon: Float = FloatEpsilon) = abs(this) <= epsilon

inline fun Double.isZero(epsilon: Double = DoubleEpsilon) = abs(this) <= epsilon

inline fun Int.clamp(minimum: Int, maximum: Int) = when {
    this < minimum -> minimum
    this > maximum -> maximum
    else -> this
}

inline fun Float.clamp(minimum: Float = 0.0f, maximum: Float = 1.0f) = when {
    this < minimum -> minimum
    this > maximum -> maximum
    else -> this
}

inline fun Double.clamp(minimum: Double = 0.0, maximum: Double = 1.0): Double = when {
    this < minimum -> minimum
    this > maximum -> maximum
    else -> this
}

fun Int.wrap(low: Int, high: Int): Int {
    val r = high - low
    var t = (this - low) % r
    if (t < 0) t += r
    return t + low
}

fun Float.wrap(low: Float, high: Float): Float {
    val r = high - low
    var t = (this - low) % r
    if (t < 0.0f) t += r
    return t + low
}

fun Double.wrap(low: Double, high: Double): Double {
    val r = high - low
    var t = (this - low) % r
    if (t < 0.0) t += r
    return t + low
}

fun smoothStep(low: Float, high: Float, value: Float): Float {
    val t = ((value - low) / (high - low)).clamp()
    return t * t * (3 - 2 * t)
}

fun triArea(a: Float3, b: Float3, c: Float3): Float {
    val distanceABX = b.x - a.x
    val distanceABY = b.y - a.y
    val distanceABZ = b.z - a.z
    val distanceCAX = c.x - a.x
    val distanceCAY = c.y - a.y
    val distanceCAZ = c.z - a.z
    val distanceAB2 = distanceABX * distanceABX + distanceABY * distanceABY + distanceABZ * distanceABZ
    val distanceCA2 = distanceCAX * distanceCAX + distanceCAY * distanceCAY + distanceCAZ * distanceCAZ
    val area2 = distanceABX * distanceCAX + distanceABY * distanceCAY + distanceABZ * distanceCAZ
    return 0.5f * sqrt(distanceAB2 * distanceCA2 - area2 * area2)
}

fun triAspectRatio(a: Float3, b: Float3, c: Float3): Float {
    val distanceAB = a.distance(b)
    val distanceBC = b.distance(c)
    val distanceCA = c.distance(a)
    val s = (distanceAB + distanceBC + distanceCA) / 2.0f
    return abs(distanceAB * distanceBC * distanceCA / (8.0f * (s - distanceAB) * (s - distanceBC) * (s - distanceCA)))
}

fun barycentricWeights(point: Float3, a: Float3, b: Float3, c: Float3, result: MutableFloat3): MutableFloat3 {
    val distanceAB2 = MutableFloat3(b).sub(a)
    val distanceAC2 = MutableFloat3(c).sub(a)
    val normal = distanceAB2.cross(distanceAC2, MutableFloat3())

    val normalLength2 = normal.length2()
    val result0 = MutableFloat3()

    distanceAB2.set(c).sub(b)
    distanceAC2.set(point).sub(b)
    result.x = (normal * distanceAB2.cross(distanceAC2, result0)) / normalLength2

    distanceAB2.set(a).sub(c)
    distanceAC2.set(point).sub(c)
    result.y = (normal * distanceAB2.cross(distanceAC2, result0)) / normalLength2

    distanceAB2.set(b).sub(a)
    distanceAC2.set(point).sub(a)
    result.z = (normal * distanceAB2.cross(distanceAC2, result0)) / normalLength2

    return result
}

const val Sqrt1_2 = 0.707106781f
