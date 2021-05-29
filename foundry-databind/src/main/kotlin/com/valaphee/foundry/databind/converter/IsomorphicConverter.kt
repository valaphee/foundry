/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.converter

/**
 * @author Kevin Ludwig
 */
interface IsomorphicConverter<S, T> : Converter<S, T> {
    fun convertBack(target: T): S

    fun reverseConverter() = object : Converter<T, S> {
        override fun invoke(source: T) = convertBack(source)
    }
}
