/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject

/**
 * @author Kevin Ludwig
 */
object DefaultApplicationHolder {
    @get:JvmStatic
    var singularityApplication: DefaultApplication? = null
}
