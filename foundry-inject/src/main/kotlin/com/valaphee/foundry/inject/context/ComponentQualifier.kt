/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

import java.lang.reflect.Parameter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.WildcardType

/**
 * @author Kevin Ludwig
 */
interface ComponentQualifier {
    val type: Class<Any>?
}

@Suppress("UNCHECKED_CAST")
fun Parameter.extractQualifier(): ComponentQualifier = getAnnotation(Qualifier::class.java)?.value?.let { NameComponentQualifier(it, type as Class<Any>) } ?: TypeComponentQualifier(type as Class<Any>, parameterizedType.extractGenericType())

@Suppress("UNCHECKED_CAST")
private fun Type.extractGenericType(): Class<Any>? = when (val type = (this as? ParameterizedType)?.actualTypeArguments?.getOrNull(0)) {
    is Class<*> -> type as Class<Any>
    is WildcardType -> type.upperBounds.firstOrNull() as? Class<Any>
    is ParameterizedType -> type.extractGenericType()
    else -> null
}
