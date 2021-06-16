/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

import java.util.*
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.withLock

/**
 * @author Kevin Ludwig
 */
class DefaultApplicationContext(
    internal val components: DefaultComponents<Any> = DefaultComponents()
) : ApplicationContext, Components<Any> by components {
    private val factoryProducerRegistry: MutableMap<String, ComponentFactoryProducer> = mutableMapOf(
        "singleton" to SingletonComponentFactoryProducer(components),
        "prototype" to PrototypeComponentFactoryProducer(components)
    )
    private val lock: ReadWriteLock = ReentrantReadWriteLock()

    override fun registerScope(scope: String, factoryProducer: ComponentFactoryProducer) = lock.writeLock().withLock { factoryProducerRegistry[scope] = factoryProducer }

    override fun unregisterScope(scope: String): Unit = lock.writeLock().withLock { factoryProducerRegistry.remove(scope) }

    override fun <T : Any> define(type: Class<T>, name: String?, scope: String, constructor: ComponentConstructor<T>?) = lock.writeLock().withLock {
        if (components.containsStrict(type)) throw ComponentAlreadyExistsException("Class $type already has a direct component factory")
        val factoryProducer = factoryProducerRegistry[scope] ?: throw ComponentLoadException("No component factory producer found for scope $scope")
        if (name != null && name in components) throw ComponentAlreadyExistsException("Name $name already exists for class $type")
        components.put(factoryProducer.produceComponentFactory(type, name ?: components.computeName(type), constructor).also { it.state = ComponentFactoryState.Loaded })
    }

    override fun <T : Any> define(componentFactory: ComponentFactory<T>) = lock.writeLock().withLock {
        if (components.containsStrict(componentFactory.type)) throw ComponentAlreadyExistsException("Class ${componentFactory.type} already has a direct component factory")
        if (componentFactory.name in components) throw ComponentAlreadyExistsException("Name ${componentFactory.name} already exists for class ${componentFactory.type}")
        componentFactory.state = ComponentFactoryState.Loaded
        components.put(componentFactory)
    }

    override fun link() = lock.writeLock().withLock { components.filter { it.state == ComponentFactoryState.Loaded }.onEach(ComponentFactory<*>::link).onEach(this::checkForCircularDependency).onEach(ComponentFactory<*>::initialize) }

    @Suppress("UNCHECKED_CAST")
    private fun checkForCircularDependency(componentFactory: ComponentFactory<*>) {
        val queue: Queue<Array<ComponentFactory<*>>> = LinkedList()
        queue.add(arrayOf(componentFactory))
        while (queue.isNotEmpty()) {
            val history = queue.poll()
            val factory = history.last() as AbstractComponentFactory<out Any>
            val provider = factory.provider as DefaultComponentProvider<out Any>
            provider.parameterFactories.flatMap { parameterFactory -> parameterFactory.run { components.factories() } }.onEach { iteratedFactory -> if (componentFactory === iteratedFactory) throw ComponentCircularDependencyException("Circular dependency detected with trace ${history.joinToString()} for $iteratedFactory") }.map { iteratedFactory -> history + iteratedFactory }.forEach { newHistory -> queue.add(newHistory) }
        }
    }

    override fun remove(name: String): ComponentFactory<out Any>? = components.remove(name)

    override fun <T : Any> remove(type: Class<T>): Components<T> = components.remove(type)

    override fun <T : Any> removeStrict(type: Class<T>): ComponentFactory<*>? = removeStrict(type)
}
