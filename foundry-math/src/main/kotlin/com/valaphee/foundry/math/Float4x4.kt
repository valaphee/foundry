/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.math

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

/**
 * @author Kevin Ludwig
 */
open class Float4x4 {
    var matrix = FloatArray(16)
        protected set

    init {
        setIdentity()
    }

    operator fun get(index: Int) = matrix[index]

    operator fun get(row: Int, column: Int) = matrix[column * 4 + row]

    operator fun set(index: Int, value: Float) {
        matrix[index] = value
    }

    operator fun set(row: Int, column: Int, value: Float) {
        matrix[column * 4 + row] = value
    }

    fun getRow(row: Int, result: MutableFloat4): MutableFloat4 {
        result.x = this[row, 0]
        result.y = this[row, 1]
        result.z = this[row, 2]
        result.w = this[row, 3]
        return result
    }

    fun setRow(row: Int, value: Float3, w: Float) {
        this[row, 0] = value.x
        this[row, 1] = value.y
        this[row, 2] = value.z
        this[row, 3] = w
    }

    fun setRow(row: Int, value: Float4) {
        this[row, 0] = value.x
        this[row, 1] = value.y
        this[row, 2] = value.z
        this[row, 3] = value.w
    }

    fun getColumn(column: Int, result: MutableFloat4): MutableFloat4 {
        result.x = this[0, column]
        result.y = this[1, column]
        result.z = this[2, column]
        result.w = this[3, column]
        return result
    }

    fun setColumn(column: Int, value: Float3, w: Float) {
        this[0, column] = value.x
        this[1, column] = value.y
        this[2, column] = value.z
        this[3, column] = w
    }

    fun setColumn(column: Int, value: Float4) {
        this[0, column] = value.x
        this[1, column] = value.y
        this[2, column] = value.z
        this[3, column] = value.w
    }

    fun set(value: Float4x4) = apply {
        for (i in 0..15) matrix[i] = value.matrix[i]
    }

    fun set(value: Double4x4) = apply {
        for (i in 0..15) matrix[i] = value.matrix[i].toFloat()
    }

    fun set(value: List<Float>) = apply {
        for (i in 0..15) matrix[i] = value[i]
    }

    fun setZero() = apply {
        for (i in 0..15) matrix[i] = 0.0f
    }

    fun setIdentity() = apply {
        setZero()
        matrix[0] = 1.0f
        matrix[5] = 1.0f
        matrix[10] = 1.0f
        matrix[15] = 1.0f
    }

    fun setRotate(value: Float, axisX: Float, axisY: Float, axisZ: Float) = apply {
        var axisXVar = axisX
        var axisYVar = axisY
        var axisZVar = axisZ

        matrix[3] = 0.0f
        matrix[7] = 0.0f
        matrix[11] = 0.0f
        matrix[12] = 0.0f
        matrix[13] = 0.0f
        matrix[14] = 0.0f
        matrix[15] = 1.0f

        val rad = value.toRad()
        val sin = sin(rad)
        val cos = cos(rad)

        if (axisXVar > 0.0f && axisYVar == 0.0f && axisZVar == 0.0f) {
            matrix[5] = cos
            matrix[10] = cos
            matrix[6] = sin
            matrix[9] = -sin
            matrix[1] = 0.0f
            matrix[2] = 0.0f
            matrix[4] = 0.0f
            matrix[8] = 0.0f
            matrix[0] = 1.0f
        } else if (axisXVar == 0.0f && axisYVar > 0.0f && axisZVar == 0.0f) {
            matrix[0] = cos
            matrix[10] = cos
            matrix[8] = sin
            matrix[2] = -sin
            matrix[1] = 0.0f
            matrix[4] = 0.0f
            matrix[6] = 0.0f
            matrix[9] = 0.0f
            matrix[5] = 1.0f
        } else if (axisXVar == 0.0f && axisYVar == 0.0f && axisZVar > 0.0f) {
            matrix[0] = cos
            matrix[5] = cos
            matrix[1] = sin
            matrix[4] = -sin
            matrix[2] = 0.0f
            matrix[6] = 0.0f
            matrix[8] = 0.0f
            matrix[9] = 0.0f
            matrix[10] = 1.0f
        } else {
            val reciprocalLength = 1.0f / sqrt(axisXVar * axisXVar + axisYVar * axisYVar + axisZVar * axisZVar)
            axisXVar *= reciprocalLength
            axisYVar *= reciprocalLength
            axisZVar *= reciprocalLength

            val vers = 1.0f - cos
            val xy = axisXVar * axisYVar
            val yz = axisYVar * axisZVar
            val zx = axisZVar * axisXVar
            val xs = axisXVar * sin
            val ys = axisYVar * sin
            val zs = axisZVar * sin
            matrix[0] = axisXVar * axisXVar * vers + cos
            matrix[4] = xy * vers - zs
            matrix[8] = zx * vers + ys
            matrix[1] = xy * vers + zs
            matrix[5] = axisYVar * axisYVar * vers + cos
            matrix[9] = yz * vers - xs
            matrix[2] = zx * vers - ys
            matrix[6] = yz * vers + xs
            matrix[10] = axisZVar * axisZVar * vers + cos
        }
    }

    fun setRotation(quaternion: Float4) = apply {
        val w = quaternion.w
        val x = quaternion.x
        val y = quaternion.y
        val z = quaternion.z

        var value = sqrt(w * w + x * x + y * y + z * z)
        value = 1.0f / (value * value)

        this[0, 0] = 1.0f - 2.0f * value * (y * y + z * z)
        this[0, 1] = 2.0f * value * (x * y - z * w)
        this[0, 2] = 2.0f * value * (x * z + y * w)
        this[0, 3] = 0.0f
        this[1, 0] = 2.0f * value * (x * y + z * w)
        this[1, 1] = 1.0f - 2.0f * value * (x * x + z * z)
        this[1, 2] = 2.0f * value * (y * z - x * w)
        this[1, 3] = 0.0f
        this[2, 0] = 2.0f * value * (x * z - y * w)
        this[2, 1] = 2.0f * value * (y * z + x * w)
        this[2, 2] = 1.0f - 2.0f * value * (x * x + y * y)
        this[2, 3] = 0.0f
        this[3, 0] = 0.0f
        this[3, 1] = 0.0f
        this[3, 2] = 0.0f
        this[3, 3] = 1.0f
    }

    fun setRotation(value: Float3x3) = apply {
        for (row in 0..2) for (column in 0..2) this[row, column] = value[row, column]
        scale(1.0f / sqrt(this[0, 0] * this[0, 0] + this[1, 0] * this[1, 0] + this[2, 0] * this[2, 0] + this[3, 0] * this[3, 0]))
    }

    fun setRotation(value: Float4x4) = apply {
        for (row in 0..2) for (col in 0..2) this[row, col] = value[row, col]
        scale(1.0f / sqrt(this[0, 0] * this[0, 0] + this[1, 0] * this[1, 0] + this[2, 0] * this[2, 0] + this[3, 0] * this[3, 0]))
    }

    fun setLookAt(position: Float3, lookAt: Float3, up: Float3) = apply {
        var fx = lookAt.x - position.x
        var fy = lookAt.y - position.y
        var fz = lookAt.z - position.z

        val rlf = 1.0f / sqrt(fx * fx + fy * fy + fz * fz)
        fx *= rlf
        fy *= rlf
        fz *= rlf

        var sx = fy * up.z - fz * up.y
        var sy = fz * up.x - fx * up.z
        var sz = fx * up.y - fy * up.x

        val rls = 1.0f / sqrt(sx * sx + sy * sy + sz * sz)
        sx *= rls
        sy *= rls
        sz *= rls

        matrix[0] = sx
        matrix[1] = sy * fz - sz * fy
        matrix[2] = -fx
        matrix[3] = 0.0f
        matrix[4] = sy
        matrix[5] = sz * fx - sx * fz
        matrix[6] = -fy
        matrix[7] = 0.0f
        matrix[8] = sz
        matrix[9] = sx * fy - sy * fx
        matrix[10] = -fz
        matrix[11] = 0.0f
        matrix[12] = 0.0f
        matrix[13] = 0.0f
        matrix[14] = 0.0f
        matrix[15] = 1.0f

        translate(-position.x, -position.y, -position.z)
    }

    fun setOrthographic(left: Float, right: Float, bottom: Float, top: Float, near: Float, far: Float) = apply {
        require(left != right)
        require(bottom != top)
        require(near != far)

        val width = 1.0f / (right - left)
        val height = 1.0f / (top - bottom)
        val depth = 1.0f / (far - near)
        matrix[0] = 2.0f * width
        matrix[5] = 2.0f * height
        matrix[10] = -2.0f * depth
        matrix[12] = -(right + left) * width
        matrix[13] = -(top + bottom) * height
        matrix[14] = -(far + near) * depth
        matrix[15] = 1.0f
        matrix[1] = 0.0f
        matrix[2] = 0.0f
        matrix[3] = 0.0f
        matrix[4] = 0.0f
        matrix[6] = 0.0f
        matrix[7] = 0.0f
        matrix[8] = 0.0f
        matrix[9] = 0.0f
        matrix[11] = 0.0f
    }

    fun setPerspective(fovY: Float, aspect: Float, near: Float, far: Float) = apply {
        val value = 1.0f / tan(fovY * (PI / 360.0)).toFloat()
        val rangeReciprocal = 1.0f / (near - far)

        matrix[0] = value / aspect
        matrix[1] = 0.0f
        matrix[2] = 0.0f
        matrix[3] = 0.0f
        matrix[4] = 0.0f
        matrix[5] = value
        matrix[6] = 0.0f
        matrix[7] = 0.0f
        matrix[8] = 0.0f
        matrix[9] = 0.0f
        matrix[10] = (far + near) * rangeReciprocal
        matrix[11] = -1.0f
        matrix[12] = 0.0f
        matrix[13] = 0.0f
        matrix[14] = 2.0f * far * near * rangeReciprocal
        matrix[15] = 0.0f
    }

    fun getOrigin(result: MutableFloat3): MutableFloat3 {
        result.x = this[0, 3]
        result.y = this[1, 3]
        result.z = this[2, 3]
        return result
    }

    fun getRotation(result: Float3x3): Float3x3 {
        result[0, 0] = this[0, 0]
        result[0, 1] = this[0, 1]
        result[0, 2] = this[0, 2]
        result[1, 0] = this[1, 0]
        result[1, 1] = this[1, 1]
        result[1, 2] = this[1, 2]
        result[2, 0] = this[2, 0]
        result[2, 1] = this[2, 1]
        result[2, 2] = this[2, 2]
        return result
    }

    fun getRotationTransposed(result: Float3x3): Float3x3 {
        result[0, 0] = this[0, 0]
        result[0, 1] = this[1, 0]
        result[0, 2] = this[2, 0]
        result[1, 0] = this[0, 1]
        result[1, 1] = this[1, 1]
        result[1, 2] = this[2, 1]
        result[2, 0] = this[0, 2]
        result[2, 1] = this[1, 2]
        result[2, 2] = this[2, 2]
        return result
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

    fun transpose() = transpose(Float4x4())

    fun transpose(result: Float4x4): Float4x4 {
        for (i in 0..3) {
            val baseIndex = i * 4
            result.matrix[i] = matrix[baseIndex]
            result.matrix[i + 4] = matrix[baseIndex + 1]
            result.matrix[i + 8] = matrix[baseIndex + 2]
            result.matrix[i + 12] = matrix[baseIndex + 3]
        }
        return result
    }

    fun invert(epsilon: Float = 0.0f): Boolean {
        val result = Float4x4()
        return invert(result, epsilon).also { if (it) set(result) }
    }

    fun invert(result: Float4x4, epsilon: Float = 0.0f): Boolean {
        val src0 = matrix[0]
        val src4 = matrix[1]
        val src8 = matrix[2]
        val src12 = matrix[3]
        val src1 = matrix[4]
        val src5 = matrix[5]
        val src9 = matrix[6]
        val src13 = matrix[7]
        val src2 = matrix[8]
        val src6 = matrix[9]
        val src10 = matrix[10]
        val src14 = matrix[11]
        val src3 = matrix[12]
        val src7 = matrix[13]
        val src11 = matrix[14]
        val src15 = matrix[15]
        val atmp0 = src10 * src15
        val atmp1 = src11 * src14
        val atmp2 = src9 * src15
        val atmp3 = src11 * src13
        val atmp4 = src9 * src14
        val atmp5 = src10 * src13
        val atmp6 = src8 * src15
        val atmp7 = src11 * src12
        val atmp8 = src8 * src14
        val atmp9 = src10 * src12
        val atmp10 = src8 * src13
        val atmp11 = src9 * src12
        val dst0 = atmp0 * src5 + atmp3 * src6 + atmp4 * src7 - (atmp1 * src5 + atmp2 * src6 + atmp5 * src7)
        val dst1 = atmp1 * src4 + atmp6 * src6 + atmp9 * src7 - (atmp0 * src4 + atmp7 * src6 + atmp8 * src7)
        val dst2 = atmp2 * src4 + atmp7 * src5 + atmp10 * src7 - (atmp3 * src4 + atmp6 * src5 + atmp11 * src7)
        val dst3 = atmp5 * src4 + atmp8 * src5 + atmp11 * src6 - (atmp4 * src4 + atmp9 * src5 + atmp10 * src6)
        val dst4 = atmp1 * src1 + atmp2 * src2 + atmp5 * src3 - (atmp0 * src1 + atmp3 * src2 + atmp4 * src3)
        val dst5 = atmp0 * src0 + atmp7 * src2 + atmp8 * src3 - (atmp1 * src0 + atmp6 * src2 + atmp9 * src3)
        val dst6 = atmp3 * src0 + atmp6 * src1 + atmp11 * src3 - (atmp2 * src0 + atmp7 * src1 + atmp10 * src3)
        val dst7 = atmp4 * src0 + atmp9 * src1 + atmp10 * src2 - (atmp5 * src0 + atmp8 * src1 + atmp11 * src2)
        val btmp0 = src2 * src7
        val btmp1 = src3 * src6
        val btmp2 = src1 * src7
        val btmp3 = src3 * src5
        val btmp4 = src1 * src6
        val btmp5 = src2 * src5
        val btmp6 = src0 * src7
        val btmp7 = src3 * src4
        val btmp8 = src0 * src6
        val btmp9 = src2 * src4
        val btmp10 = src0 * src5
        val btmp11 = src1 * src4
        val dst8 = btmp0 * src13 + btmp3 * src14 + btmp4 * src15 - (btmp1 * src13 + btmp2 * src14 + btmp5 * src15)
        val dst9 = btmp1 * src12 + btmp6 * src14 + btmp9 * src15 - (btmp0 * src12 + btmp7 * src14 + btmp8 * src15)
        val dst10 = btmp2 * src12 + btmp7 * src13 + btmp10 * src15 - (btmp3 * src12 + btmp6 * src13 + btmp11 * src15)
        val dst11 = btmp5 * src12 + btmp8 * src13 + btmp11 * src14 - (btmp4 * src12 + btmp9 * src13 + btmp10 * src14)
        val dst12 = btmp2 * src10 + btmp5 * src11 + btmp1 * src9 - (btmp4 * src11 + btmp0 * src9 + btmp3 * src10)
        val dst13 = btmp8 * src11 + btmp0 * src8 + btmp7 * src10 - (btmp6 * src10 + btmp9 * src11 + btmp1 * src8)
        val dst14 = btmp6 * src9 + btmp11 * src11 + btmp3 * src8 - (btmp10 * src11 + btmp2 * src8 + btmp7 * src9)
        val dst15 = btmp10 * src10 + btmp4 * src8 + btmp9 * src9 - (btmp8 * src9 + btmp11 * src10 + btmp5 * src8)
        var determinant = src0 * dst0 + src1 * dst1 + src2 * dst2 + src3 * dst3
        return if (determinant.isZero(epsilon)) false else {
            determinant = 1.0f / determinant
            result.matrix[0] = dst0 * determinant
            result.matrix[1] = dst1 * determinant
            result.matrix[2] = dst2 * determinant
            result.matrix[3] = dst3 * determinant
            result.matrix[4] = dst4 * determinant
            result.matrix[5] = dst5 * determinant
            result.matrix[6] = dst6 * determinant
            result.matrix[7] = dst7 * determinant
            result.matrix[8] = dst8 * determinant
            result.matrix[9] = dst9 * determinant
            result.matrix[10] = dst10 * determinant
            result.matrix[11] = dst11 * determinant
            result.matrix[12] = dst12 * determinant
            result.matrix[13] = dst13 * determinant
            result.matrix[14] = dst14 * determinant
            result.matrix[15] = dst15 * determinant
            true
        }
    }

    fun add(value: Float4x4) = apply {
        for (i in 0..15) matrix[i] += value.matrix[i]
    }

    fun mul(value: Float4x4) = apply {
        val result = Float4x4()
        mul(value, result)
        set(result)
    }

    fun mul(value: Float4x4, result: Float4x4): Float4x4 {
        for (i in 0..3) for (j in 0..3) {
            var x = 0.0f
            for (k in 0..3) x += this[j + k * 4] * value[i * 4 + k]
            result[i * 4 + j] = x
        }
        return result
    }

    fun translate(x: Float, y: Float, z: Float) = apply {
        for (i in 0..3) matrix[12 + i] += matrix[i] * x + matrix[4 + i] * y + matrix[8 + i] * z
    }

    fun translate(value: Float3) = translate(value.x, value.y, value.z)

    fun translate(x: Float, y: Float, z: Float, result: Float4x4): Float4x4 {
        for (i in 0..11) result.matrix[i] = matrix[i]
        for (i in 0..3) result.matrix[12 + i] = matrix[i] * x + matrix[4 + i] * y + matrix[8 + i] * z + matrix[12 + i]
        return result
    }

    fun rotate(value: Float, axisX: Float, axisY: Float, axisZ: Float) = set(mul(Float4x4().setRotate(value, axisX, axisY, axisZ), Float4x4()))

    fun rotate(value: Float, axis: Float3) = rotate(value, axis.x, axis.y, axis.z)

    fun rotate(value: Float, axisX: Float, axisY: Float, axisZ: Float, result: Float4x4) = mul(Float4x4().setRotate(value, axisX, axisY, axisZ), result)

    fun rotate(value: Float, axis: Float3, result: Float4x4) = rotate(value, axis.x, axis.y, axis.z, result)

    fun rotate(value: Float3x3) = mul(Float4x4().setRotation(value), this)

    fun scale(value: Float) = scale(value, value, value)

    fun scale(x: Float, y: Float, z: Float) = apply {
        for (i in 0..3) {
            matrix[i] *= x
            matrix[4 + i] *= y
            matrix[8 + i] *= z
        }
    }

    fun scale(value: Float3) = scale(value.x, value.y, value.z)

    fun scale(x: Float, y: Float, z: Float, result: Float4x4): Float4x4 {
        for (i in 0..3) {
            result.matrix[i] = matrix[i] * x
            result.matrix[4 + i] = matrix[4 + i] * y
            result.matrix[8 + i] = matrix[8 + i] * z
            result.matrix[12 + i] = matrix[12 + i]
        }
        return result
    }

    fun resetScale() = apply { scale(1.0f / sqrt(this[0, 0] * this[0, 0] + this[1, 0] * this[1, 0] + this[2, 0] * this[2, 0]), 1.0f / sqrt(this[0, 1] * this[0, 1] + this[1, 1] * this[1, 1] + this[2, 1] * this[2, 1]), 1.0f / sqrt(this[0, 2] * this[0, 2] + this[1, 2] * this[1, 2] + this[2, 2] * this[2, 2])) }

    fun transform(value: MutableFloat3, w: Float = 1.0f): MutableFloat3 {
        val x = value.x * this[0, 0] + value.y * this[0, 1] + value.z * this[0, 2] + w * this[0, 3]
        val y = value.x * this[1, 0] + value.y * this[1, 1] + value.z * this[1, 2] + w * this[1, 3]
        val z = value.x * this[2, 0] + value.y * this[2, 1] + value.z * this[2, 2] + w * this[2, 3]
        return value.set(x, y, z)
    }

    fun transform(value: Float3, w: Float = 1.0f, result: MutableFloat3): MutableFloat3 {
        result.x = value.x * this[0, 0] + value.y * this[0, 1] + value.z * this[0, 2] + w * this[0, 3]
        result.y = value.x * this[1, 0] + value.y * this[1, 1] + value.z * this[1, 2] + w * this[1, 3]
        result.z = value.x * this[2, 0] + value.y * this[2, 1] + value.z * this[2, 2] + w * this[2, 3]
        return result
    }

    fun transform(value: MutableFloat4): MutableFloat4 {
        val x = value.x * this[0, 0] + value.y * this[0, 1] + value.z * this[0, 2] + value.w * this[0, 3]
        val y = value.x * this[1, 0] + value.y * this[1, 1] + value.z * this[1, 2] + value.w * this[1, 3]
        val z = value.x * this[2, 0] + value.y * this[2, 1] + value.z * this[2, 2] + value.w * this[2, 3]
        val w = value.x * this[3, 0] + value.y * this[3, 1] + value.z * this[3, 2] + value.w * this[3, 3]
        return value.set(x, y, z, w)
    }

    fun transform(value: Float4, result: MutableFloat4): MutableFloat4 {
        result.x = value.x * this[0, 0] + value.y * this[0, 1] + value.z * this[0, 2] + value.w * this[0, 3]
        result.y = value.x * this[1, 0] + value.y * this[1, 1] + value.z * this[1, 2] + value.w * this[1, 3]
        result.z = value.x * this[2, 0] + value.y * this[2, 1] + value.z * this[2, 2] + value.w * this[2, 3]
        result.w = value.x * this[3, 0] + value.y * this[3, 1] + value.z * this[3, 2] + value.w * this[3, 3]
        return result
    }

    fun toList() = listOf(
        matrix[0], matrix[1], matrix[2], matrix[3],
        matrix[4], matrix[5], matrix[6], matrix[7],
        matrix[8], matrix[9], matrix[10], matrix[11],
        matrix[12], matrix[13], matrix[14], matrix[15],
    )
}

/**
 * @author Kevin Ludwig
 */
class Float4x4Stack(
    val stackSize: Int = DefaultStackSize
) : Float4x4() {
    companion object {
        const val DefaultStackSize = 32
    }

    private var stackIndex = 0
    private val stack = FloatArray(16 * stackSize)

    fun push() = apply {
        require(stackIndex < stackSize)
        val offset = stackIndex * 16
        for (i in 0..15) stack[offset + i] = matrix[i]
        stackIndex++
        setIdentity()
    }

    fun pop() = apply {
        require(stackIndex > 0)
        stackIndex--
        val offset = stackIndex * 16
        for (i in 0..15) matrix[i] = stack[offset + i]
    }

    fun reset() = apply {
        stackIndex = 0
        setIdentity()
    }
}
