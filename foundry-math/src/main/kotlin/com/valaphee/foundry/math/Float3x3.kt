/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.math

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class Float3x3 {
    val matrix = FloatArray(9)

    init {
        setIdentity()
    }

    operator fun get(index: Int) = matrix[index]

    operator fun get(row: Int, column: Int) = matrix[column * 3 + row]

    operator fun set(index: Int, value: Float) {
        matrix[index] = value
    }

    operator fun set(row: Int, column: Int, value: Float) {
        matrix[column * 3 + row] = value
    }

    fun set(value: Float3x3) = apply {
        for (i in 0..8) this[i] = value[i]
    }

    fun set(value: List<Float>) {
        for (i in 0..8) this[i] = value[i]
    }

    fun setColumn(column: Int, value: Float3) {
        this[0, column] = value.x
        this[1, column] = value.y
        this[2, column] = value.z
    }

    fun getColumn(column: Int, result: MutableFloat3): MutableFloat3 {
        result.x = this[0, column]
        result.y = this[1, column]
        result.z = this[2, column]
        return result
    }

    fun setIdentity() = apply {
        for (i in 1..8) this[i] = 0.0f
        for (i in 0..8 step 4) this[i] = 1.0f
    }

    fun setRotate(x: Float, y: Float, z: Float) = apply {
        val radX = x.toRad()
        val radY = y.toRad()
        val radZ = z.toRad()

        val cosX = cos(radX)
        val cosY = cos(radY)
        val cosZ = cos(radZ)
        val sinX = sin(radX)
        val sinY = sin(radY)
        val sinZ = sin(radZ)
        val cosXZ = cosX * cosZ
        val cosXSinZ = cosX * sinZ
        val sinXCosZ = sinX * cosZ
        val sinXZ = sinX * sinZ

        matrix[0] = cosY * cosZ
        matrix[3] = sinY * sinXCosZ - cosXSinZ
        matrix[6] = sinY * cosXZ + sinXZ
        matrix[1] = cosY * sinZ
        matrix[4] = sinY * sinXZ + cosXZ
        matrix[7] = sinY * cosXSinZ - sinXCosZ
        matrix[2] = -sinY
        matrix[5] = cosY * sinX
        matrix[8] = cosY * cosX
    }

    fun setRotation(value: Float, axisX: Float, axisY: Float, axisZ: Float) = apply {
        var axisXVar = axisX
        var axisYVar = axisY
        var axisZVar = axisZ
        val length = sqrt(axisXVar * axisXVar + axisYVar * axisYVar + axisZVar * axisZVar)
        if (!(1.0f - length).isZero()) {
            val reciprocalLength = 1.0f / length
            axisXVar *= reciprocalLength
            axisYVar *= reciprocalLength
            axisZVar *= reciprocalLength
        }

        val rad = value.toRad()
        val sin = sin(rad)
        val cos = cos(rad)
        val vers = 1.0f - cos
        val xy = axisXVar * axisYVar
        val yz = axisYVar * axisZVar
        val zx = axisZVar * axisXVar
        val xs = axisXVar * sin
        val ys = axisYVar * sin
        val zs = axisZVar * sin
        this[0] = axisXVar * axisXVar * vers + cos
        this[3] = xy * vers - zs
        this[6] = zx * vers + ys
        this[1] = xy * vers + zs
        this[4] = axisYVar * axisYVar * vers + cos
        this[7] = yz * vers - xs
        this[2] = zx * vers - ys
        this[5] = yz * vers + xs
        this[8] = axisZVar * axisZVar * vers + cos
    }

    fun setRotation(quaternion: Float4) = apply {
        val r = quaternion.w
        val i = quaternion.x
        val j = quaternion.y
        val k = quaternion.z

        var s = sqrt(r * r + i * i + j * j + k * k)
        s = 1.0f / (s * s)

        this[0, 0] = 1.0f - 2.0f * s * (j * j + k * k)
        this[0, 1] = 2.0f * s * (i * j - k * r)
        this[0, 2] = 2.0f * s * (i * k + j * r)

        this[1, 0] = 2.0f * s * (i * j + k * r)
        this[1, 1] = 1.0f - 2.0f * s * (i * i + k * k)
        this[1, 2] = 2.0f * s * (j * k - i * r)

        this[2, 0] = 2.0f * s * (i * k - j * r)
        this[2, 1] = 2.0f * s * (j * k + i * r)
        this[2, 2] = 1.0f - 2.0f * s * (i * i + j * j)
    }

    fun getRotation(result: MutableFloat4): MutableFloat4 {
        val trace = this[0, 0] + this[1, 1] + this[2, 2]

        if (trace > 0.0f) {
            var s = sqrt(trace + 1.0f)
            result.w = s * 0.5f
            s = 0.5f / s

            result.x = (this[2, 1] - this[1, 2]) * s
            result.y = (this[0, 2] - this[2, 0]) * s
            result.z = (this[1, 0] - this[0, 1]) * s
        } else {
            val i = if (this[0, 0] < this[1, 1]) if (this[1, 1] < this[2, 2]) 2 else 1 else if (this[0, 0] < this[2, 2]) 2 else 0
            val j = (i + 1) % 3
            val k = (i + 2) % 3

            var s = sqrt(this[i, i] - this[j, j] - this[k, k] + 1.0f)
            result[i] = s * 0.5f
            s = 0.5f / s

            result.w = (this[k, j] - this[j, k]) * s
            result[j] = (this[j, i] + this[i, j]) * s
            result[k] = (this[k, i] + this[i, k]) * s
        }
        return result
    }

    fun transpose(): Float3x3 {
        var swap = this[1]
        this[1] = this[3]
        this[3] = swap
        swap = this[2]
        this[2] = this[6]
        this[6] = swap
        swap = this[5]
        this[5] = this[7]
        this[7] = swap
        return this
    }

    fun transpose(result: Float4x4): Float4x4 {
        result[0] = this[0]
        result[1] = this[3]
        result[2] = this[6]
        result[3] = this[1]
        result[4] = this[4]
        result[5] = this[7]
        result[6] = this[2]
        result[7] = this[5]
        result[8] = this[8]
        return result
    }

    fun invert(): Boolean {
        val inverted = Float3x3()
        return invert(inverted).also { if (it) set(inverted) }
    }

    fun invert(result: Float3x3): Boolean {
        var determinant = 0.0f
        for (i in 0..2) {
            determinant += (this[i] * (this[3 + (i + 1) % 3] * this[6 + (i + 2) % 3] - this[3 + (i + 2) % 3] * this[6 + (i + 1) % 3]))
        }

        return if (determinant > 0.0f) {
            determinant = 1.0f / determinant
            for (i in 0..2) for (j in 0..2) result[j * 3 + i] = ((this[((i + 1) % 3) * 3 + (j + 1) % 3] * this[((i + 2) % 3) * 3 + (j + 2) % 3]) - (this[((i + 1) % 3) * 3 + (j + 2) % 3] * this[((i + 2) % 3) * 3 + (j + 1) % 3])) * determinant
            true
        } else false
    }

    fun mul(value: Float3x3) = mul(value, this)

    fun mul(value: Float3x3, result: Float3x3): Float3x3 {
        for (i in 0..2) for (j in 0..2) {
            var x = 0.0f
            for (k in 0..2) x += this[j + k * 3] * value[i * 3 + k]
            result[i * 3 + j] = x
        }
        return result
    }

    fun rotate(x: Float, y: Float, z: Float) = set(mul(Float3x3().setRotate(x, y, z), Float3x3()))

    fun rotate(value: Float, axisX: Float, axisY: Float, axisZ: Float) = mul(Float3x3().setRotation(value, axisX, axisY, axisZ), Float3x3())

    fun rotate(value: Float, axis: Float3) = rotate(value, axis.x, axis.y, axis.z)

    fun rotate(x: Float, y: Float, z: Float, result: Float3x3): Float3x3 {
        result.set(this)
        result.rotate(x, y, z)
        return result
    }

    fun rotate(value: Float, axisX: Float, axisY: Float, axisZ: Float, result: Float3x3): Float3x3 {
        result.set(this)
        result.rotate(value, axisX, axisY, axisZ)
        return result
    }

    fun rotate(value: Float, axis: Float3, result: Float3x3) = rotate(value, axis.x, axis.y, axis.z, result)

    fun scale(x: Float, y: Float, z: Float) = apply {
        for (i in 0..2) {
            matrix[i] *= x
            matrix[3 + i] *= y
            matrix[6 + i] *= z
        }
    }

    fun scale(value: Float3) = scale(value.x, value.y, value.z)

    fun scale(x: Float, y: Float, z: Float, result: Float3x3): Float3x3 {
        for (i in 0..2) {
            result.matrix[i] = matrix[i] * x
            result.matrix[3 + i] = matrix[3 + i] * y
            result.matrix[6 + i] = matrix[6 + i] * z
        }
        return result
    }

    fun transform(value: MutableFloat3): MutableFloat3 {
        val x = value.x * this[0, 0] + value.y * this[0, 1] + value.z * this[0, 2]
        val y = value.x * this[1, 0] + value.y * this[1, 1] + value.z * this[1, 2]
        val z = value.x * this[2, 0] + value.y * this[2, 1] + value.z * this[2, 2]
        return value.set(x, y, z)
    }

    fun transform(value: Float3, result: MutableFloat3): MutableFloat3 {
        result.x = value.x * this[0, 0] + value.y * this[0, 1] + value.z * this[0, 2]
        result.y = value.x * this[1, 0] + value.y * this[1, 1] + value.z * this[1, 2]
        result.z = value.x * this[2, 0] + value.y * this[2, 1] + value.z * this[2, 2]
        return result
    }
}
