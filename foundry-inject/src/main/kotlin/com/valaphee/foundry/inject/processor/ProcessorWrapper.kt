/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.processor

import com.valaphee.foundry.inject.DefaultApplication
import com.valaphee.foundry.inject.DefaultApplicationAware
import com.valaphee.foundry.inject.context.ApplicationContextAware
import java.lang.reflect.Method
import kotlin.reflect.KClass

private val validProcessors = arrayOf(
    AddClassProcessor::class,
    InitialInstanceProcessor::class,
    RemoveClassProcessor::class,
    NoopProcessor::class
)

/**
 * @author Kevin Ludwig
 */
class ProcessorWrapper(
    defaultApplication: DefaultApplication,
    val implementation: Any,
    val methods: Map<KClass<out Annotation>, List<Method>> = implementation.javaClass.methods.groupBy { it.annotations.map(Annotation::annotationClass).firstOrNull(validProcessors::contains) ?: NoopProcessor::class }
) {
    init {
        if (implementation is ApplicationContextAware) implementation.applicationContext = defaultApplication.applicationContext
        if (implementation is DefaultApplicationAware) implementation.defaultApplication = defaultApplication
    }

    inline operator fun <reified T : Annotation> invoke(vararg context: Any?) = methods[T::class]?.forEach { it(implementation, *context) }
}

/**
 * @author Kevin Ludwig
 */
internal annotation class NoopProcessor
