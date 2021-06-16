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
}
