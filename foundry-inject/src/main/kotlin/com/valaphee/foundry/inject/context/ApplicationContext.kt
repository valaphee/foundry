/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

/**
 * @author Kevin Ludwig
 */
interface ApplicationContext : Components<Any> {
    fun registerScope(scope: String, factoryProducer: ComponentFactoryProducer)

    fun unregisterScope(scope: String)

    @Throws(ComponentLoadException::class)
    fun <T : Any> define(type: Class<T>, name: String? = null, scope: String = "singleton", constructor: ComponentConstructor<T>? = null)

    @Throws(ComponentAlreadyExistsException::class)
    fun <T : Any> define(componentFactory: ComponentFactory<T>)

    @Throws(ComponentLinkException::class)
    fun link(): Iterable<ComponentFactory<*>>

    fun remove(name: String): ComponentFactory<*>?

    fun <T : Any> remove(type: Class<T>): Components<T>

    fun <T : Any> removeStrict(type: Class<T>): ComponentFactory<*>?
}

@Throws(ComponentLoadException::class)
inline fun <reified T : Any> ApplicationContext.define(name: String? = null, scope: String = "singleton", constructor: ComponentConstructor<T>? = null) = define(T::class.java, name, scope, constructor)

inline fun <reified T : Any> ApplicationContext.remove() = remove(T::class.java)

inline fun <reified T : Any> ApplicationContext.removeStrict() = removeStrict(T::class.java)
