/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

/**
 * @author Kevin Ludwig
 */
class SingletonComponentConstructor<T>(
    val value: T
) : ComponentConstructor<T> {
    override val parameterTypes: List<ComponentQualifier> = emptyList()

    override fun invoke(vararg parameters: Any) = value
}
