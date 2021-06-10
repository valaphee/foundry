/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

class DefaultComponentProvider<T>(
    private val clazz: Class<T>,
    private val components: DefaultComponents<*>,
    private val constructor: ComponentConstructor<T>
) : ComponentProvider<T> {
    lateinit var parameterFactories: List<ParameterFactory>

    override fun link() {
        parameterFactories = constructor.parameterTypes.map {
            if (it.type == List::class.java) ListParameterFactory(it)
            else SimpleParameterFactory(it)
        }
    }

    override fun invoke() = constructor(*parameterFactories.map { it.run { components.instantiateParameter() } }.toTypedArray())
}

interface ParameterFactory {
    fun DefaultComponents<*>.factories(): Iterable<ComponentFactory<*>>

    fun DefaultComponents<*>.instantiateParameter(): Any
}

internal class SimpleParameterFactory(
    private val componentQualifier: ComponentQualifier
) : ParameterFactory {
    override fun DefaultComponents<*>.factories() = getLinked(componentQualifier)()?.let { listOf(it) } ?: emptyList()

    override fun DefaultComponents<*>.instantiateParameter() = getLinked(componentQualifier)()?.invoke() ?: throw ComponentLinkNotFoundException("Component definition for Class $componentQualifier not found")
}

internal class ListParameterFactory(
    private val componentQualifier: ComponentQualifier
) : ParameterFactory {
    override fun DefaultComponents<*>.factories(): Iterable<ComponentFactory<*>> = if (componentQualifier is TypeComponentQualifier && componentQualifier.genericType != null) {
        getLinked(TypeComponentQualifier(componentQualifier.genericType)).toList()
    } else if (componentQualifier is NameComponentQualifier) {
        getLinked(componentQualifier).toList()
    } else {
        emptyList()
    }

    override fun DefaultComponents<*>.instantiateParameter() = factories().flatMap(ComponentFactory<*>::toList)
}
