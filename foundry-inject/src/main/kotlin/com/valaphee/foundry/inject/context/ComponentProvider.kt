/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

/**
 * @author Kevin Ludwig
 */
interface ComponentProvider<T> {
    @Throws(ComponentLoadException::class)
    fun link()

    @Throws(ComponentInstantiationException::class)
    operator fun invoke(): T
}