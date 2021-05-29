/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.math.collision

import com.valaphee.foundry.math.Float3
import com.valaphee.foundry.math.MutableDouble3
import com.valaphee.foundry.math.MutableFloat3
import com.valaphee.foundry.math.clamp
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

/**
 * @author Kevin Ludwig
 */
class BoundingBox() {
    private val _minimum = MutableFloat3()
    val minimum: Float3 get() = _minimum

    private val _maximum = MutableFloat3()
    val maximum: Float3 get() = _maximum

    private val _size = MutableFloat3()
    val size: Float3 get() = _size

    private val _center = MutableFloat3()
    val center: Float3 get() = _center

    var isEmpty = true
        private set

    var isBatchUpdate = false
        set(value) {
            field = value
            if (!value) updateSizeAndCenter()
        }

    constructor(minimum: Float3, maximum: Float3) : this() {
        set(minimum, maximum)
    }

    constructor(minimumX: Float, minimumY: Float, minimumZ: Float, maximumX: Float, maximumY: Float, maximumZ: Float) : this() {
        set(minimumX, minimumY, minimumZ, maximumX, maximumY, maximumZ)
    }

    inline fun batchUpdate(block: BoundingBox.() -> Unit) {
        val wasBatchUpdate = isBatchUpdate
        isBatchUpdate = true
        block()
        isBatchUpdate = wasBatchUpdate
    }

    fun set(value: BoundingBox) = apply {
        _minimum.set(value.minimum)
        _maximum.set(value.maximum)
        _size.set(value.size)
        _center.set(value.center)
        isEmpty = value.isEmpty
        return this
    }

    fun set(minimum: Float3, maximum: Float3) = apply {
        _minimum.set(minimum)
        _maximum.set(maximum)
        isEmpty = false
        updateSizeAndCenter()
    }

    fun set(minimumX: Float, minimumY: Float, minimumZ: Float, maximumX: Float, maximumY: Float, maximumZ: Float) = apply {
        _minimum.set(minimumX, minimumY, minimumZ)
        _maximum.set(maximumX, maximumY, maximumZ)
        isEmpty = false
        updateSizeAndCenter()
    }

    fun setMerged(a: BoundingBox, b: BoundingBox) = apply {
        _minimum.x = min(a.minimum.x, b.minimum.x)
        _minimum.y = min(a.minimum.y, b.minimum.y)
        _minimum.z = min(a.minimum.z, b.minimum.z)
        _maximum.x = max(a.maximum.x, b.maximum.x)
        _maximum.y = max(a.maximum.y, b.maximum.y)
        _maximum.z = max(a.maximum.z, b.maximum.z)

        isEmpty = false
        updateSizeAndCenter()
    }

    fun move(value: Float3) = move(value.x, value.y, value.z)

    fun move(x: Float, y: Float, z: Float) = apply {
        check(!isEmpty)
        _minimum.x += x
        _minimum.y += y
        _minimum.z += z
        _maximum.x += x
        _maximum.y += y
        _maximum.z += z
        _center.x += x
        _center.y += y
        _center.z += z
    }

    fun add(point: Float3) = apply {
        addPoint(point)
        updateSizeAndCenter()
    }

    fun add(points: List<Float3>) = apply {
        add(points, points.indices)
    }

    fun add(points: List<Float3>, range: IntRange) = apply {
        range.forEach { addPoint(points[it]) }
        updateSizeAndCenter()
    }

    fun add(boundingBox: BoundingBox) = apply {
        if (!boundingBox.isEmpty) {
            addPoint(boundingBox.minimum)
            addPoint(boundingBox.maximum)
            updateSizeAndCenter()
        }
    }

    fun expand(value: Float3) = apply {
        check(!isEmpty)
        _minimum -= value
        _maximum += value
        updateSizeAndCenter()
    }

    fun signedExpand(value: Float3) = apply {
        check(!isEmpty)
        if (value.x > 0) _maximum.x += value.x else _minimum.x += value.x
        if (value.y > 0) _maximum.y += value.y else _minimum.y += value.y
        if (value.z > 0) _maximum.z += value.z else _minimum.z += value.z
        updateSizeAndCenter()
    }

    fun clear() = apply {
        isEmpty = true
        _minimum.set(Float3.Zero)
        _maximum.set(Float3.Zero)
        updateSizeAndCenter()
    }

    operator fun contains(point: Float3) = point.x >= minimum.x && point.x <= maximum.x && point.y >= minimum.y && point.y <= maximum.y && point.z >= minimum.z && point.z <= maximum.z

    fun contains(x: Float, y: Float, z: Float) = x >= minimum.x && x <= maximum.x && y >= minimum.y && y <= maximum.y && z >= minimum.z && z <= maximum.z

    operator fun contains(boundingBox: BoundingBox) = boundingBox.minimum in this && boundingBox.maximum in this

    fun intersects(boundingBox: BoundingBox) = minimum.x <= boundingBox.maximum.x && maximum.x >= boundingBox.minimum.x && minimum.y <= boundingBox.maximum.y && maximum.y >= boundingBox.minimum.y && minimum.z <= boundingBox.maximum.z && maximum.z >= boundingBox.minimum.z

    fun clampToBounds(point: MutableFloat3) {
        point.x = point.x.clamp(minimum.x, maximum.x)
        point.y = point.y.clamp(minimum.y, maximum.y)
        point.z = point.z.clamp(minimum.z, maximum.z)
    }

    fun clampToBounds(point: MutableDouble3) {
        point.x = point.x.clamp(minimum.x.toDouble(), maximum.x.toDouble())
        point.y = point.y.clamp(minimum.y.toDouble(), maximum.y.toDouble())
        point.z = point.z.clamp(minimum.z.toDouble(), maximum.z.toDouble())
    }

    fun pointDistance2(point: Float3): Float {
        if (point in this) return 0.0f

        var x = 0.0f
        var result = point.x - minimum.x
        if (result < 0.0f) x = result else {
            result = maximum.x - point.x
            if (result < 0.0f) x = result
        }
        var y = 0.0f
        result = point.y - minimum.y
        if (result < 0.0f) y = result else {
            result = maximum.y - point.y
            if (result < 0.0f) y = result
        }
        var z = 0.0f
        result = point.z - minimum.z
        if (result < 0.0f) z = result else {
            result = maximum.z - point.z
            if (result < 0.0f) z = result
        }

        return x * x + y * y + z * z
    }

    fun pointDistance(point: Float3) = sqrt(pointDistance2(point).toDouble()).toFloat()

    fun hitDistance2(ray: Ray): Float {
        if (isEmpty) return Float.MAX_VALUE
        if (ray.origin in this) return 0.0f

        var result = 1.0f / ray.direction.x
        var minimumX: Float
        var maximumX: Float
        if (result >= 0.0f) {
            minimumX = (minimum.x - ray.origin.x) * result
            maximumX = (maximum.x - ray.origin.x) * result
        } else {
            minimumX = (maximum.x - ray.origin.x) * result
            maximumX = (minimum.x - ray.origin.x) * result
        }

        result = 1.0f / ray.direction.y
        val minimumY: Float
        val maximumY: Float
        if (result >= 0.0f) {
            minimumY = (minimum.y - ray.origin.y) * result
            maximumY = (maximum.y - ray.origin.y) * result
        } else {
            minimumY = (maximum.y - ray.origin.y) * result
            maximumY = (minimum.y - ray.origin.y) * result
        }

        if (minimumX > maximumY || minimumY > maximumX) return Float.MAX_VALUE
        if (minimumY > minimumX) minimumX = minimumY
        if (maximumY < maximumX) maximumX = maximumY

        result = 1.0f / ray.direction.z
        val minimumZ: Float
        val maximumZ: Float
        if (result >= 0.0f) {
            minimumZ = (minimum.z - ray.origin.z) * result
            maximumZ = (maximum.z - ray.origin.z) * result
        } else {
            minimumZ = (maximum.z - ray.origin.z) * result
            maximumZ = (minimum.z - ray.origin.z) * result
        }

        if (minimumX > maximumZ || minimumZ > maximumX) return Float.MAX_VALUE
        if (minimumZ > minimumX) minimumX = minimumZ

        return if (minimumX > 0) {
            var distance = ray.direction.x * minimumX
            var distance2 = distance * distance
            distance = ray.direction.y * minimumX
            distance2 += distance * distance
            distance = ray.direction.z * minimumX
            distance2 += distance * distance
            distance2 / ray.direction.length2()
        } else Float.MAX_VALUE
    }

    fun equals(other: BoundingBox) = isEmpty == other.isEmpty && minimum.equals(other.minimum) && maximum.equals(other.maximum)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BoundingBox) return false

        if (isEmpty != other.isEmpty) return false
        if (minimum != other.minimum) return false
        if (maximum != other.maximum) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isEmpty.hashCode()
        result = 31 * result + minimum.hashCode()
        result = 31 * result + maximum.hashCode()
        return result
    }

    private fun updateSizeAndCenter() {
        if (!isBatchUpdate) {
            _maximum.sub(_minimum, _size)
            size.scale(0.5f, _center).add(minimum)
        }
    }

    private fun addPoint(point: Float3) {
        if (isEmpty) {
            _minimum.set(point)
            _maximum.set(point)
            isEmpty = false
        } else {
            if (point.x < minimum.x) _minimum.x = point.x
            if (point.y < minimum.y) _minimum.y = point.y
            if (point.z < minimum.z) _minimum.z = point.z
            if (point.x > maximum.x) _maximum.x = point.x
            if (point.y > maximum.y) _maximum.y = point.y
            if (point.z > maximum.z) _maximum.z = point.z
        }
    }
}
