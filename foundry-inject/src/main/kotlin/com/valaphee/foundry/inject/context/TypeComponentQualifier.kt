/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

import kotlin.reflect.KClass

/**
 * @author Kevin Ludwig
 */
data class TypeComponentQualifier(
    override val type: Class<Any>,
    val genericType: Class<Any>? = null
) : ComponentQualifier

fun Class<Any>.qualifier(genericType: Class<Any>? = null) = TypeComponentQualifier(this, genericType)

fun KClass<Any>.qualifier(genericType: KClass<Any>? = null) = TypeComponentQualifier(java, genericType?.java)
