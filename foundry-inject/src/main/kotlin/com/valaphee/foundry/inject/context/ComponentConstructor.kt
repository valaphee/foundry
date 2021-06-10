/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

/**
 * @author Kevin Ludwig
 */
interface ComponentConstructor<T> {
    val parameterTypes: List<ComponentQualifier>

    @Throws(Throwable::class)
    operator fun invoke(vararg parameters: Any): T
}
