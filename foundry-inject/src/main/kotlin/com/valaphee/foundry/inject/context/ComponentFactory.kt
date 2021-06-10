/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

/**
 * @author Kevin Ludwig
 */
interface ComponentFactory<T> : Iterable<T> {
    val name: String
    val type: Class<T>
    val scope: String
    var state: ComponentFactoryState

    @Throws(ComponentLinkException::class)
    fun link()

    @Throws(ComponentInstantiationException::class)
    fun initialize()

    @Throws(ComponentInstantiationException::class)
    operator fun invoke(context: Any? = null): T
}
