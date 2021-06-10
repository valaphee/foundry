/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

/**
 * @author Kevin Ludwig
 */
abstract class AbstractComponentFactory<T>(
    override val name: String,
    override val type: Class<T>,
    override val scope: String,
    val provider: ComponentProvider<T>,
    override var state: ComponentFactoryState = ComponentFactoryState.New
) : ComponentFactory<T> {
    override fun link() {
        if (state != ComponentFactoryState.Loaded) throw ComponentLinkNotFoundException("Can only link loaded component factories")

        provider.link()
        state = ComponentFactoryState.Ready
    }

    override fun initialize() {}

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AbstractComponentFactory<*>

        if (name != other.name) return false

        return true
    }

    override fun hashCode() = name.hashCode()

    override fun toString() = "ComponentFactory(name=$name, type=$type, scope=$scope, state=$state)"
}
