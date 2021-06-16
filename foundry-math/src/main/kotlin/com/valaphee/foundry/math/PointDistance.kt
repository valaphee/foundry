/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.math

import com.valaphee.foundry.math.collision.Ray
import kotlin.math.sqrt

fun Float3.distanceToLine(lineA: Float3, lineB: Float3) = sqrt(distanceToLine2(lineA, lineB))

fun Float3.distanceToLine2(lineA: Float3, lineB: Float3) = distancePointToLine2(x, y, z, lineA, lineB)

fun distancePointToLine2(x: Float, y: Float, z: Float, lineA: Float3, lineB: Float3): Float {
    val distanceX = lineB.x - lineA.x
    val distanceY = lineB.y - lineA.y
    val distanceZ = lineB.z - lineA.z

    val dotDistancePoint = x * distanceX + y * distanceY + z * distanceZ
    val dotDistanceLineA = lineA.x * distanceX + lineA.y * distanceY + lineA.z * distanceZ
    val distance2 = distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ

    val length = (dotDistancePoint - dotDistanceLineA) / distance2
    val toLineX = distanceX * length + lineA.x
    val toLineY = distanceY * length + lineA.y
    val toLineZ = distanceZ * length + lineA.z

    val distanceToLineX = toLineX - x
    val distanceToLineY = toLineY - y
    val distanceToLineZ = toLineZ - z
    return distanceToLineX * distanceToLineX + distanceToLineY * distanceToLineY + distanceToLineZ * distanceToLineZ
}

fun Float3.nearestPointOnLine(lineA: Float3, lineB: Float3, result: MutableFloat3): MutableFloat3 {
    lineB.sub(lineA, result)
    return result.scale((dot(result) - lineA * result) / (result * result)).add(lineA)
}

fun Float3.distanceToRay(ray: Ray) = distanceToRay(ray.origin, ray.direction)

fun Float3.distanceToRay(origin: Float3, direction: Float3) = sqrt(distanceToRay2(origin, direction))

fun Float3.distanceToRay2(ray: Ray) = distanceToRay2(ray.origin, ray.direction)

fun Float3.distanceToRay2(origin: Float3, direction: Float3) = distancePointToRay2(x, y, z, origin, direction)

fun distancePointToRay2(x: Float, y: Float, z: Float, origin: Float3, direction: Float3): Float {
    val distanceX: Float
    val distanceY: Float
    val distanceZ: Float
    val dot = x * direction.x + y * direction.y + z * direction.z
    val length = (dot - origin * direction) / (direction * direction)
    if (length <= 0.0f) {
        distanceX = origin.x - x
        distanceY = origin.y - y
        distanceZ = origin.z - z
    } else {
        distanceX = direction.x * length + origin.x - x
        distanceY = direction.y * length + origin.y - y
        distanceZ = direction.z * length + origin.z - z
    }
    return distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ
}

fun Float3.nearestPointOnRay(origin: Float3, direction: Float3, result: MutableFloat3): MutableFloat3 {
    val length = (dot(direction) - origin * direction) / (direction * direction)
    return if (length <= 0.0f) result.set(origin) else result.set(direction).scale(length).add(origin)
}

fun Float2.distanceToEdge(edgeA: Float2, edgeB: Float2) = sqrt(distanceToEdge2(edgeA, edgeB))

fun Float2.distanceToEdge2(edgeA: Float2, edgeB: Float2) = distancePointToEdge2(x, y, edgeA, edgeB)

fun distancePointToEdge2(x: Float, y: Float, edgeA: Float2, edgeB: Float2): Float {
    val distanceX = edgeB.x - edgeA.x
    val distanceY = edgeB.y - edgeA.y

    val dotDistancePoint = x * distanceX + y * distanceY
    val dotDistanceEdgeA = edgeA.x * distanceX + edgeA.y * distanceY
    val distance2 = distanceX * distanceX + distanceY * distanceY

    val length = (dotDistancePoint - dotDistanceEdgeA) / distance2
    val toEdgeX: Float
    val toEdgeY: Float
    when {
        length <= 0 -> {
            toEdgeX = edgeA.x
            toEdgeY = edgeA.y
        }
        length >= 1 -> {
            toEdgeX = edgeB.x
            toEdgeY = edgeB.y
        }
        else -> {
            toEdgeX = distanceX * length + edgeA.x
            toEdgeY = distanceY * length + edgeA.y
        }
    }

    val distanceToEdgeX = toEdgeX - x
    val distanceToEdgeY = toEdgeY - y
    return distanceToEdgeX * distanceToEdgeX + distanceToEdgeY * distanceToEdgeY
}

fun Float2.nearestPointOnEdge(edgeA: Float2, edgeB: Float2, result: MutableFloat2): MutableFloat2 {
    edgeB.sub(edgeA, result)
    val length = (dot(result) - edgeA * result) / (result * result)
    return when {
        length <= 0 -> result.set(edgeA)
        length >= 1 -> result.set(edgeB)
        else -> result.scale(length).add(edgeA)
    }
}

fun Float3.distanceToEdge(edgeA: Float3, edgeB: Float3) = sqrt(distanceToEdge2(edgeA, edgeB))

fun Float3.distanceToEdge2(edgeA: Float3, edgeB: Float3) = distancePointToEdge2(x, y, z, edgeA, edgeB)

fun distancePointToEdge2(x: Float, y: Float, z: Float, edgeA: Float3, edgeB: Float3): Float {
    val distanceX = edgeB.x - edgeA.x
    val distanceY = edgeB.y - edgeA.y
    val distanceZ = edgeB.z - edgeA.z

    val dotDistancePoint = x * distanceX + y * distanceY + z * distanceZ
    val dotDistanceEdgeA = edgeA.x * distanceX + edgeA.y * distanceY + edgeA.z * distanceZ
    val distance2 = distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ

    val length = (dotDistancePoint - dotDistanceEdgeA) / distance2
    val toEdgeX: Float
    val toEdgeY: Float
    val toEdgeZ: Float
    when {
        length <= 0 -> {
            toEdgeX = edgeA.x
            toEdgeY = edgeA.y
            toEdgeZ = edgeA.z
        }
        length >= 1 -> {
            toEdgeX = edgeB.x
            toEdgeY = edgeB.y
            toEdgeZ = edgeB.z
        }
        else -> {
            toEdgeX = distanceX * length + edgeA.x
            toEdgeY = distanceY * length + edgeA.y
            toEdgeZ = distanceZ * length + edgeA.z
        }
    }

    val distanceToEdgeX = toEdgeX - x
    val distanceToEdgeY = toEdgeY - y
    val distanceToEdgeZ = toEdgeZ - z
    return distanceToEdgeX * distanceToEdgeX + distanceToEdgeY * distanceToEdgeY + distanceToEdgeZ * distanceToEdgeZ
}

fun Float3.nearestPointOnEdge(edgeA: Float3, edgeB: Float3, result: MutableFloat3): MutableFloat3 {
    edgeB.sub(edgeA, result)
    val length = (dot(result) - edgeA * result) / (result * result)
    return when {
        length <= 0 -> result.set(edgeA)
        length >= 1 -> result.set(edgeB)
        else -> result.scale(length).add(edgeA)
    }
}
