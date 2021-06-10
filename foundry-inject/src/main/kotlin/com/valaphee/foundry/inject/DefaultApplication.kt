/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject

import com.valaphee.foundry.inject.context.ApplicationContext
import com.valaphee.foundry.inject.context.DefaultApplicationContext
import com.valaphee.foundry.inject.processor.AddClassProcessor
import com.valaphee.foundry.inject.processor.InitialInstanceProcessor
import com.valaphee.foundry.inject.processor.Processor
import com.valaphee.foundry.inject.processor.ProcessorWrapper
import com.valaphee.foundry.inject.processor.RemoveClassProcessor

/**
 * @author Kevin Ludwig
 */
abstract class DefaultApplication(
    open val environment: Set<String>,
    val applicationContext: ApplicationContext = DefaultApplicationContext(),
    private val processors: MutableMap<Class<*>, ProcessorWrapper> = mutableMapOf(),
    private val classes: MutableSet<Class<*>> = mutableSetOf()
) {
    @Synchronized
    fun addClass(`class`: Class<*>) {
        if (Processor::class in `class` && `class` !in processors) processors[`class`] = ProcessorWrapper(this, `class`.getDeclaredConstructor().newInstance())
        if (Indexed::class in `class` && classes.add(`class`)) process<AddClassProcessor>(`class`)
    }

    fun addClasses(vararg classes: Class<*>) = classes.forEach(this::addClass)

    fun addClasses(classes: Iterable<Class<*>>) = classes.forEach(this::addClass)

    inline fun <reified T> addClass() = addClass(T::class.java)

    @Synchronized
    operator fun contains(`class`: Class<*>) = `class` in classes || `class` in processors

    @Synchronized
    fun removeClass(`class`: Class<*>) = (processors.remove(`class`) != null) || classes.remove(`class`).also { if (it) process<RemoveClassProcessor>(`class`) }

    fun removeClasses(vararg classes: Class<*>) = classes.forEach(this::removeClass)

    fun removeClasses(classes: Iterable<Class<*>>) = classes.forEach(this::removeClass)

    inline fun <reified T> removeClass() = removeClass(T::class.java)

    operator fun invoke() {
        while (true) applicationContext.link().flatten().onEach { process<InitialInstanceProcessor>(it) }.takeIf { it.isEmpty() }?.run { return }
    }

    operator fun contains(passedEnvironment: String) = passedEnvironment in environment

    private inline fun <reified T : Annotation> process(vararg context: Any?) = processors.values.forEach { it<T>(*context) }
}
