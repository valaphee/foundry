/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.processor

import com.valaphee.foundry.inject.contains
import com.valaphee.foundry.inject.context.ApplicationContext
import com.valaphee.foundry.inject.context.ApplicationContextAware
import com.valaphee.foundry.inject.context.Configuration
import com.valaphee.foundry.inject.context.MethodBasedComponentConstructor
import com.valaphee.foundry.inject.context.Provide
import com.valaphee.foundry.inject.context.Qualifier
import com.valaphee.foundry.inject.context.Scope
import com.valaphee.foundry.inject.first

/**
 * @author Kevin Ludwig
 */
@Processor
class ConfigurationProvideProcessor : ApplicationContextAware {
    override lateinit var applicationContext: ApplicationContext

    @AddClassProcessor
    fun onConfigurationClassAdd(clazz: Class<Any>) {
        if (Configuration::class !in clazz) return

        clazz.methods.filterNot { it.returnType == Void.TYPE || it.returnType == Unit.javaClass }.map { it to it.getAnnotation(Provide::class.java) }.forEach { (method, provide) ->
            if (provide == null) return@forEach
            applicationContext.define(method.returnType, (method first Qualifier::class)?.value, (method first Scope::class)?.value ?: "singleton", MethodBasedComponentConstructor(clazz, method))
        }
    }

    @RemoveClassProcessor
    fun onClassRemove(clazz: Class<Any>) {
        if (Configuration::class !in clazz) return

        clazz.methods.filterNot { it.returnType == Void.TYPE || it.returnType == Unit.javaClass }.filter { it.getAnnotation(Provide::class.java) != null }.forEach { applicationContext.removeStrict(it.returnType) }
    }
}
