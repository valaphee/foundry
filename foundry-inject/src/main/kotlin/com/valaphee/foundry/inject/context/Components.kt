/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

/**
 * @author Kevin Ludwig
 */
interface Components<T> : Iterable<ComponentFactory<out T>> {
    operator fun get(componentQualifier: ComponentQualifier): Components<*>

    operator fun get(name: String): ComponentFactory<out T>?

    operator fun <R : T> get(type: Class<R>): Components<R>

    fun <R : T> getStrict(type: Class<R>): ComponentFactory<R>?

    fun getInstance(name: String): T?

    fun <R : T> getInstance(type: Class<R>): R?

    operator fun contains(name: String): Boolean

    operator fun <R : T> contains(type: Class<R>): Boolean

    fun containsStrict(type: Class<*>): Boolean

    operator fun invoke(): ComponentFactory<out T>?
}

@Suppress("EXTENSION_SHADOWED_BY_MEMBER", "UNCHECKED_CAST")
inline fun <reified R : Any> Components<Any>.get(name: String) = get(name) as? ComponentFactory<R>

inline fun <reified R : Any> Components<Any>.get() = get(R::class.java)

inline fun <reified R : Any> Components<Any>.getStrict() = getStrict(R::class.java)

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun <reified R : Any> Components<Any>.getInstance(name: String) = getInstance(name) as? R

inline fun <reified R : Any> Components<Any>.getInstance() = getInstance(R::class.java)

inline fun <reified R : Any> Components<Any>.contains() = contains(R::class.java)

inline fun <reified R : Any> Components<Any>.containsStrict() = containsStrict(R::class.java)

@Suppress("UNCHECKED_CAST")
inline fun <reified R : Any> Components<Any>.primary() = this() as? ComponentFactory<R>
