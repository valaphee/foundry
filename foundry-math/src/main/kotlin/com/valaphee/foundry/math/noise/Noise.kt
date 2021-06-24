/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.math.noise

/**
 * @author Kevin Ludwig
 */
interface Noise {
    operator fun get(seed: Int, x: Float, y: Float): Float

    operator fun get(seed: Int, x: Float, y: Float, z: Float): Float
}
