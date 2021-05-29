/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.converter

/**
 * @author Kevin Ludwig
 */
interface Converter<S, T> {
    operator fun invoke(source: S): T
}

