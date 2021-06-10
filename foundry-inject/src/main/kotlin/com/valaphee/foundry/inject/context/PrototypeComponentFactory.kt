/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

import java.lang.ref.WeakReference

/**
 * @author Kevin Ludwig
 */
class PrototypeComponentFactory<T>(
    name: String,
    type: Class<T>,
    provider: ComponentProvider<T>,
    private val instances: MutableList<WeakReference<T>> = mutableListOf()
) : AbstractComponentFactory<T>(name, type, "prototype", provider) {
    @Synchronized
    override fun invoke(context: Any?) = provider.invoke().also(this::addInstance)

    private fun addInstance(instance: T) = instances().add(WeakReference(instance))

    private fun instances(): MutableList<WeakReference<T>> {
        instances.removeAll { it.get() == null }
        return instances
    }

    override fun iterator(): Iterator<T> = instances().mapNotNull(WeakReference<T>::get).iterator()
}