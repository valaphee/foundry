/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.converter

fun <S : Any, T : Any> ((S) -> T).toConverter() = object : Converter<S, T> {
    override fun invoke(source: S) = this@toConverter(source)
}
