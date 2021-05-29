/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.converter

/**
 * @author Kevin Ludwig
 */
class IdentityConverter<T : Any> : IsomorphicConverter<T, T> {
    override fun invoke(source: T) = source

    override fun convertBack(target: T) = target
}
