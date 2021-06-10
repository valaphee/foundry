/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

/**
 * @author Kevin Ludwig
 */
class SingletonComponentFactoryProducer(
    private val components: DefaultComponents<*>
) : AbstractComponentFactoryProducer() {
    override fun <T> _produceComponentFactory(type: Class<T>, name: String, constructor: ComponentConstructor<T>) = SingletonComponentFactory(name, type, DefaultComponentProvider(type, components, constructor))
}
