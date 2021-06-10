/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

import java.lang.reflect.Constructor
import java.lang.reflect.Parameter

/**
 * @author Kevin Ludwig
 */
class ConstructorBasedComponentConstructor<T>(
    private val `class`: Class<T>,
    private val constructor: Constructor<*> = `class`.constructors.firstOrNull() ?: throw ComponentConstructorNotFoundException("No constructor for class $`class` found"),
    override val parameterTypes: List<ComponentQualifier> = constructor.parameters.map(Parameter::extractQualifier)
) : ComponentConstructor<T> {
    @Suppress("UNCHECKED_CAST")
    override fun invoke(vararg parameters: Any) = try {
        constructor.newInstance(*parameters) as? T ?: throw RuntimeException("Constructor invocation returned null")
    } catch (th: Throwable) {
        throw ComponentInstantiationException("Class $`class` can not be instantiated with constructor $constructor", th)
    }
}
