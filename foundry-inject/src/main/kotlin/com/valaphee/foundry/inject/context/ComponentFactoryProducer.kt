/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

/**
 * @author Kevin Ludwig
 */
interface ComponentFactoryProducer {
    @Throws(ComponentLoadException::class)
    fun <T> produceComponentFactory(type: Class<T>, name: String, constructor: ComponentConstructor<T>?): ComponentFactory<T>
}
