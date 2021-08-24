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

package com.valaphee.foundry.math.noise

import com.valaphee.foundry.math.Float2
import com.valaphee.foundry.math.Float3

private const val xPrime = 1619
private const val yPrime = 31337
private const val zPrime = 6971
private val gradient2 = arrayOf(
    Float2(-1f, -1f), Float2(1f, -1f), Float2(-1f, 1f), Float2(1f, 1f),
    Float2(1f, -1f), Float2(-1f, 1f), Float2(1f, 1f), Float2(1f, 1f)
)
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
