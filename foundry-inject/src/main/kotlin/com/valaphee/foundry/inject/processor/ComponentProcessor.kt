/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.processor

import com.valaphee.foundry.inject.contains
import com.valaphee.foundry.inject.context.ApplicationContext
import com.valaphee.foundry.inject.context.ApplicationContextAware
import com.valaphee.foundry.inject.context.Component
import com.valaphee.foundry.inject.context.Configuration
import com.valaphee.foundry.inject.context.Qualifier
import com.valaphee.foundry.inject.context.Scope
import com.valaphee.foundry.inject.invoke

/**
 * @author Kevin Ludwig
 */
@Processor
class ComponentProcessor : ApplicationContextAware {
    override lateinit var applicationContext: ApplicationContext

    @AddClassProcessor
    fun onClassAdded(clazz: Class<Any>) {
        if (Component::class !in clazz) return

        applicationContext.define(clazz, clazz(Qualifier::class)?.value, (if (Configuration::class in clazz) null else clazz(Scope::class)?.value) ?: "singleton")
    }

    @RemoveClassProcessor
    fun onClassRemoved(clazz: Class<Any>) = applicationContext.removeStrict(clazz)
}
