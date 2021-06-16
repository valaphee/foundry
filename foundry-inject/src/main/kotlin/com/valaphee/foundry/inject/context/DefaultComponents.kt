/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

import com.valaphee.foundry.inject.context.Key.Companion.key
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.withLock

class DefaultComponents<T>(
    private val factoryByName: MutableMap<String, ComponentFactory<out T>> = ConcurrentHashMap(),
    private val factoryByType: MutableMap<Class<*>, MutableMap<ComponentFactory<out T>, Int>> = ConcurrentHashMap(),
    private val lock: ReadWriteLock = ReentrantReadWriteLock(),
    private var primaryFactory: ComponentFactory<out T>? = null
) : Components<T>, Iterable<ComponentFactory<out T>> by factoryByName.values {
    @Suppress("UNCHECKED_CAST")
    override fun get(componentQualifier: ComponentQualifier): Components<*> = when (componentQualifier) {
        is NameComponentQualifier -> DefaultComponents<Any>().apply {
            val factory = get(componentQualifier.name)
            primaryFactory = factory
            factory?.let(this::put)
        }
        is TypeComponentQualifier -> get(type = componentQualifier.type as? Class<out T> ?: error("Type ${componentQualifier.type} is not a subtype of this components collection"))
        else -> error("")
    }

    override fun get(name: String) = lock.readLock().withLock { factoryByName[name] }?.takeIf { it.state == ComponentFactoryState.Ready }

    @Suppress("UNCHECKED_CAST")
    override fun <R : T> get(type: Class<R>) = lock.readLock().withLock { factoryByType[type] }?.filterKeys { it.state == ComponentFactoryState.Ready }?.mapKeys { it.key as ComponentFactory<R> }?.components() ?: DefaultComponents()

    @Suppress("UNCHECKED_CAST")
    internal fun getLinked(componentQualifier: ComponentQualifier) = when (componentQualifier) {
        is NameComponentQualifier -> (this[componentQualifier.name] as? ComponentFactory<Any>).component()
        is TypeComponentQualifier -> lock.readLock().withLock { factoryByType[componentQualifier.type] }?.mapKeys { it.key as ComponentFactory<Any> }?.components() ?: DefaultComponents()
        else -> error("")
    }

    override fun getInstance(name: String) = this[name]?.invoke()

    override fun <R : T> getInstance(type: Class<R>) = this[type]()?.invoke()

    override fun contains(name: String) = lock.readLock().withLock { factoryByName[name] }?.takeIf { it.state == ComponentFactoryState.Ready } != null

    override fun <R: T> contains(type: Class<R>) = lock.readLock().withLock { factoryByType[type] }?.filterKeys { it.state == ComponentFactoryState.Ready }?.isNotEmpty() ?: false

    override fun invoke() = primaryFactory

    @Suppress("UNCHECKED_CAST")
    override fun <R : T> getStrict(type: Class<R>) = factoryByType[type]?.filterValues { it == 0 }?.keys?.firstOrNull() as? ComponentFactory<R>

    override fun containsStrict(type: Class<*>) = lock.readLock().withLock { factoryByType[type]?.any { it.value == 0 } ?: false }

    internal fun put(factory: ComponentFactory<out T>) = lock.writeLock().withLock {
        factoryByName[factory.name] = factory
        factory.type.key().parentClasses.forEach { (clazz, level) -> factoryByType.computeIfAbsent(clazz) { mutableMapOf() }[factory] = level }
    }

    internal fun putAll(factories: Iterable<ComponentFactory<out T>>) = lock.writeLock().withLock {
        factories.forEach { factory ->
            factoryByName[factory.name] = factory
            factory.type.key().parentClasses.forEach { (clazz, level) -> factoryByType.computeIfAbsent(clazz) { mutableMapOf() }[factory] = level }
        }
    }

    @Suppress("UNCHECKED_CAST")
    internal fun <R : T> remove(name: String) = lock.writeLock().withLock {
        val factory = factoryByName[name] ?: return null
        factoryByType[factory.type]?.remove(factory)
        factoryByName.remove(factory.name)
        factory.state = ComponentFactoryState.Removed
        factory as? ComponentFactory<out R>
    }

    @Suppress("UNCHECKED_CAST")
    internal fun <R : T> remove(type: Class<R>) = lock.writeLock().withLock { factoryByType.remove(type) }?.onEach { it.key.state = ComponentFactoryState.Removed }?.onEach { factoryByName.remove(it.key.name) }?.mapKeys { it.key as ComponentFactory<R> }?.components() ?: DefaultComponents()

    @Suppress("UNCHECKED_CAST")
    internal fun <R : T> removeStrict(type: Class<R>) = lock.writeLock().withLock {
        val factory = factoryByType[type]?.filterValues { it == 0 }?.keys?.firstOrNull() ?: return null
        factoryByType[type]?.remove(factory)
        factoryByName.remove(factory.name)
        factory.state = ComponentFactoryState.Removed
        factory as? ComponentFactory<out R>
    }

    internal fun computeName(clazz: Class<*>) = if (lock.readLock().withLock { clazz.simpleName in factoryByName }) clazz.name else clazz.simpleName

    internal fun <R> Map<ComponentFactory<R>, Int>.components(): Components<R> = DefaultComponents<R>().apply {
        primaryFactory = this@components.filterValues { it == 0 }.keys.firstOrNull()
        putAll(this@components.entries.sortedBy { it.value }.map { it.key })
    }

    internal fun <R> ComponentFactory<R>?.component(): Components<R> = DefaultComponents<R>().apply {
        primaryFactory = this@component
        if (this@component != null) put(this@component)
    }
}
