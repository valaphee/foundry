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
    private val byName: MutableMap<String, ComponentFactory<out T>> = ConcurrentHashMap(),
    private val byType: MutableMap<Class<*>, MutableMap<ComponentFactory<out T>, Int>> = ConcurrentHashMap(),
    private val lock: ReadWriteLock = ReentrantReadWriteLock(),
    private var primary: ComponentFactory<out T>? = null
) : Components<T>, Iterable<ComponentFactory<out T>> by byName.values {
    @Suppress("UNCHECKED_CAST")
    override fun get(componentQualifier: ComponentQualifier): Components<*> =
        when (componentQualifier) {
            is NameComponentQualifier -> DefaultComponents<Any>().apply {
                val gotByName = get(componentQualifier.name)
                primary = gotByName
                gotByName?.let(this::put)
            }
            is TypeComponentQualifier -> get(type = componentQualifier.type as? Class<out T> ?: error("Type ${componentQualifier.type} is not a subtype of this components collection"))
            else -> error("ComponentQualifier ${componentQualifier.javaClass.name} not implemented")
        }

    override fun get(name: String) = lock.readLock().withLock { byName[name] }?.takeIf { it.state == ComponentFactoryState.Ready }

    @Suppress("UNCHECKED_CAST")
    override fun <R : T> get(type: Class<R>) = lock.readLock().withLock { byType[type] }?.filterKeys { it.state == ComponentFactoryState.Ready }?.mapKeys { it.key as ComponentFactory<R> }?.components() ?: DefaultComponents()

    @Suppress("UNCHECKED_CAST")
    internal fun getLinked(componentQualifier: ComponentQualifier) = when (componentQualifier) {
        is NameComponentQualifier -> (this[componentQualifier.name] as? ComponentFactory<Any>).component()
        is TypeComponentQualifier -> lock.readLock().withLock { byType[componentQualifier.type] }?.mapKeys { it.key as ComponentFactory<Any> }?.components() ?: DefaultComponents()
        else -> error("ComponentQualifier ${componentQualifier.javaClass.name} not implemented")
    }

    override fun getInstance(name: String) = this[name]?.invoke()

    override fun <R : T> getInstance(type: Class<R>) = this[type]()?.invoke()

    override fun contains(name: String) = lock.readLock().withLock { byName[name] }?.takeIf { it.state == ComponentFactoryState.Ready } != null

    override fun <R: T> contains(type: Class<R>) = lock.readLock().withLock { byType[type] }?.filterKeys { it.state == ComponentFactoryState.Ready }?.isNotEmpty() ?: false

    override fun invoke() = primary

    @Suppress("UNCHECKED_CAST")
    override fun <R : T> getStrict(type: Class<R>) = byType[type]?.filterValues { it == 0 }?.keys?.firstOrNull() as? ComponentFactory<R>

    override fun containsStrict(type: Class<*>) = lock.readLock().withLock { byType[type]?.any { it.value == 0 } ?: false }

    internal fun put(factory: ComponentFactory<out T>) = lock.writeLock().withLock {
        byName[factory.name] = factory
        factory.type.key().parentClasses.forEach { (clazz, level) -> byType.computeIfAbsent(clazz) { mutableMapOf() }[factory] = level }
    }

    internal fun putAll(factories: Iterable<ComponentFactory<out T>>) = lock.writeLock().withLock {
        factories.forEach { factory ->
            byName[factory.name] = factory
            factory.type.key().parentClasses.forEach { (clazz, level) -> byType.computeIfAbsent(clazz) { mutableMapOf() }[factory] = level }
        }
    }

    @Suppress("UNCHECKED_CAST")
    internal fun <R : T> remove(name: String) = lock.writeLock().withLock {
        val factory = byName[name] ?: return null
        byType[factory.type]?.remove(factory)
        byName.remove(factory.name)
        factory.state = ComponentFactoryState.Removed
        factory as? ComponentFactory<out R>
    }

    @Suppress("UNCHECKED_CAST")
    internal fun <R : T> remove(type: Class<R>) = lock.writeLock().withLock { byType.remove(type) }?.onEach { it.key.state = ComponentFactoryState.Removed }?.onEach { byName.remove(it.key.name) }?.mapKeys { it.key as ComponentFactory<R> }?.components() ?: DefaultComponents()

    @Suppress("UNCHECKED_CAST")
    internal fun <R : T> removeStrict(type: Class<R>) = lock.writeLock().withLock {
        val factory = byType[type]?.filterValues { it == 0 }?.keys?.firstOrNull() ?: return null
        byType[type]?.remove(factory)
        byName.remove(factory.name)
        factory.state = ComponentFactoryState.Removed
        factory as? ComponentFactory<out R>
    }

    internal fun computeName(clazz: Class<*>) = if (lock.readLock().withLock { clazz.simpleName in byName }) clazz.name else clazz.simpleName

    internal fun <R> Map<ComponentFactory<R>, Int>.components(): Components<R> = DefaultComponents<R>().apply {
        primary = this@components.filterValues { it == 0 }.keys.firstOrNull()
        putAll(this@components.entries.sortedBy { it.value }.map { it.key })
    }

    internal fun <R> ComponentFactory<R>?.component(): Components<R> = DefaultComponents<R>().apply {
        primary = this@component
        if (this@component != null) put(this@component)
    }

    override fun toString() = "Components {primary: $primary}, {values: ${byName.values}}"
}
