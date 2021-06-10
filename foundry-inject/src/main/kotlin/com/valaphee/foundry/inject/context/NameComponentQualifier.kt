/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

/**
 * @author Kevin Ludwig
 */
data class NameComponentQualifier(
    val name: String,
    override val type: Class<Any>?
): ComponentQualifier

fun String.qualifier(type: Class<Any>? = null) = NameComponentQualifier(this, type)
