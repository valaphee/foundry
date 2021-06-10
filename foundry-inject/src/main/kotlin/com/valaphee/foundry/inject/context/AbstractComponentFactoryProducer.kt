/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

/**
 * @author Kevin Ludwig
 */
abstract class AbstractComponentFactoryProducer : ComponentFactoryProducer {
    override fun <T> produceComponentFactory(type: Class<T>, name: String, constructor: ComponentConstructor<T>?) = _produceComponentFactory(type, name, constructor ?: ConstructorBasedComponentConstructor(type))

    abstract fun <T> _produceComponentFactory(type: Class<T>, name: String, constructor: ComponentConstructor<T>): ComponentFactory<T>
}
