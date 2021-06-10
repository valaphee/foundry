/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

import java.lang.reflect.Method
import java.lang.reflect.Parameter

/**
 * @author Kevin Ludwig
 */
class MethodBasedComponentConstructor<T>(
    private val declaringClass: Class<Any>,
    private val method: Method,
    override val parameterTypes: List<ComponentQualifier> = listOf(declaringClass.qualifier()).plus(method.parameters.map(Parameter::extractQualifier))
) : ComponentConstructor<T> {
    init {
        method.isAccessible = true
    }

    @Suppress("UNCHECKED_CAST")
    override fun invoke(vararg parameters: Any) = try {
        method(parameters[0], *parameters.drop(1).toTypedArray()) as? T ?: throw RuntimeException("Method invocation $method returned null or illegal value")
    } catch (th: Throwable) {
        throw ComponentInstantiationException("Method invocation $method threw exception", th)
    }
}
