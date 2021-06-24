/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.math.noise

import com.valaphee.foundry.math.Float2
import com.valaphee.foundry.math.Float3

private const val xPrime = 1619
private const val yPrime = 31337
private const val zPrime = 6971
private val gradient2 = arrayOf(
    Float2(-1f, -1f), Float2(1f, -1f), Float2(-1f, 1f), Float2(1f, 1f),
    Float2(1f, -1f), Float2(-1f, 1f), Float2(1f, 1f), Float2(1f, 1f))
private val gradient3 = arrayOf(
    Float3(1f, 1f, 1f), Float3(-1f, 1f, 1f), Float3(1f, -1f, 1f), Float3(-1f, -1f, 1f),
    Float3(1f, 1f, 1f), Float3(-1f, 1f, 1f), Float3(1f, 1f, -1f), Float3(-1f, 1f, -1f),
    Float3(1f, 1f, 1f), Float3(1f, -1f, 1f), Float3(1f, 1f, -1f), Float3(1f, -1f, -1f),
    Float3(1f, 1f, 1f), Float3(1f, -1f, 1f), Float3(-1f, 1f, 1f), Float3(1f, -1f, -1f)
)

fun hash(seed: Int, x: Int, y: Int): Int {
    var hash = seed
    hash = hash xor xPrime * x
    hash = hash xor yPrime * y
    hash *= hash * hash * 60493
    hash = hash shr 13 xor hash
    return hash
}

fun hash(seed: Int, x: Int, y: Int, z: Int): Int {
    var hash = seed
    hash = hash xor xPrime * x
    hash = hash xor yPrime * y
    hash = hash xor zPrime * z
    hash *= hash * hash * 60493
    hash = hash shr 13 xor hash
    return hash
}

fun valHash(seed: Int, x: Int, y: Int): Float {
    var hash = seed
    hash = hash xor xPrime * x
    hash = hash xor yPrime * y
    return hash * hash * hash * 60493 / 2147483648.0.toFloat()
}

fun valHash(seed: Int, x: Int, y: Int, z: Int): Float {
    var hash = seed
    hash = hash xor xPrime * x
    hash = hash xor yPrime * y
    hash = hash xor zPrime * z
    return hash * hash * hash * 60493 / 2147483648.0.toFloat()
}

fun gradHash(seed: Int, x: Int, y: Int, xd: Float, yd: Float): Float {
    var hash = seed
    hash = hash xor xPrime * x
    hash = hash xor yPrime * y
    hash *= hash * hash * 60493
    hash = hash shr 13 xor hash
    val gradient = gradient2[hash and 7]
    return xd * gradient.x + yd * gradient.y
}

fun gradHash(seed: Int, x: Int, y: Int, z: Int, xd: Float, yd: Float, zd: Float): Float {
    var hash = seed
    hash = hash xor xPrime * x
    hash = hash xor yPrime * y
    hash = hash xor zPrime * z
    hash *= hash * hash * 60493
    hash = hash shr 13 xor hash
    val gradient = gradient3[hash and 15]
    return xd * gradient.x + yd * gradient.y + zd * gradient.z
}
