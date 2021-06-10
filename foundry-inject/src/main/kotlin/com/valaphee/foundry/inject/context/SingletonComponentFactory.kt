/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

/**
 * @author Kevin Ludwig
 */
class SingletonComponentFactory<T>(
    name: String,
    type: Class<T>,
    provider: ComponentProvider<T>,
    private var instance: T? = null
) : AbstractComponentFactory<T>(name, type, "singleton", provider) {
    override fun initialize() {
        this()
    }

    @Synchronized
    override fun invoke(context: Any?): T {
        if (instance == null) instance = provider.invoke()
        return instance!!
    }

    override fun iterator() = listOf(invoke()).iterator()
}
