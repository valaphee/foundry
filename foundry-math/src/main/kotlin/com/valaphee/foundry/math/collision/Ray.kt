/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.math.collision

import com.valaphee.foundry.math.Double4x4
import com.valaphee.foundry.math.Float3
import com.valaphee.foundry.math.Float4x4
import com.valaphee.foundry.math.MutableFloat3
import com.valaphee.foundry.math.distancePointToRay2
import com.valaphee.foundry.math.distanceToRay
import com.valaphee.foundry.math.distanceToRay2

/**
 * @author Kevin Ludwig
 */
class Ray {
    val origin = MutableFloat3()
    val direction = MutableFloat3()

    fun set(value: Ray) {
        origin.set(value.origin)
        direction.set(value.direction)
    }

    fun setFromLookAt(origin: Float3, lookAt: Float3) {
        this.origin.set(origin)
        direction.set(lookAt).sub(origin).normalize()
    }

    fun distanceToPoint(point: Float3): Float = point.distanceToRay(origin, direction)

    fun distanceToPoint2(point: Float3): Float = point.distanceToRay2(origin, direction)

    fun distanceToPoint2(x: Float, y: Float, z: Float) = distancePointToRay2(x, y, z, origin, direction)

    fun transformBy(matrix: Float4x4) {
        matrix.transform(origin)
        matrix.transform(direction, 0.0f).normalize()
    }

    fun transformBy(matrix: Double4x4) {
        matrix.transform(origin)
        matrix.transform(direction, 0.0f).normalize()
    }
}
