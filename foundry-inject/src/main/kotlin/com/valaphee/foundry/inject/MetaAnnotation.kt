/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject

import java.lang.reflect.AnnotatedElement
import java.util.LinkedList
import kotlin.reflect.KClass
import kotlin.reflect.cast

infix operator fun <A : Annotation> AnnotatedElement.get(clazz: KClass<A>): List<A> {
    val processed = mutableSetOf<AnnotatedElement>()
    val annotations = mutableListOf<A>()
    val queue = LinkedList<AnnotatedElement>()
    queue.add(this)
    while (queue.isNotEmpty()) {
        val element = queue.pop()

        if (element in processed) continue

        processed.add(element)

        try {
            element.annotations.forEach {
                if (clazz.isInstance(it)) annotations.add(clazz.cast(it))
                else queue.add(it::class.java)
            }
        } catch (ignored: ClassNotFoundException) {
        }
    }
    return annotations
}

infix operator fun <A : Annotation> AnnotatedElement.invoke(clazz: KClass<A>): A? {
    val processed = mutableSetOf<AnnotatedElement>()
    val queue = LinkedList<AnnotatedElement>()
    queue.add(this)
    while (queue.isNotEmpty()) {
        val element = queue.pop()

        if (element in processed) continue

        processed.add(element)

        try {
            element.annotations.forEach {
                if (clazz.isInstance(it)) return clazz.cast(it)
                else queue.add(it::class.java)
            }
        } catch (ignored: ClassNotFoundException) {
        }
    }
    return null
}

infix fun <A : Annotation> AnnotatedElement.first(clazz: KClass<A>): A? = this(clazz)

infix operator fun <A : Annotation> AnnotatedElement.contains(clazz: KClass<A>): Boolean {
    val processed = mutableSetOf<AnnotatedElement>()
    val queue = LinkedList<AnnotatedElement>()
    queue.add(this)
    while (queue.isNotEmpty()) {
        val element = queue.pop()

        if (element in processed) continue

        processed.add(element)

        try {
            element.annotations.forEach {
                if (clazz.isInstance(it)) return true
                else queue.add(it.annotationClass.java)
            }
        } catch (ignored: ClassNotFoundException) {
        }
    }
    return false
}