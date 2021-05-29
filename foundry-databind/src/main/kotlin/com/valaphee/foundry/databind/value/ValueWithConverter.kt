/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.value

import com.valaphee.foundry.databind.converter.Converter

/**
 * @author Kevin Ludwig
 */
data class ValueWithConverter<S : Any, T : Any>(
    val value: ObservableValue<S>,
    val convert: Converter<S, T>
)
