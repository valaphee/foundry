/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject

import com.valaphee.foundry.inject.context.DefaultApplicationContext
import com.valaphee.foundry.inject.context.define
import com.valaphee.foundry.inject.context.get
import com.valaphee.foundry.inject.context.getInstance
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ApplicationContextTest {
    @Test
    fun `test dependency injection with singleton only with generated name`() {
        val applicationContext = DefaultApplicationContext()
        applicationContext.define<TypeAll>(scope = "singleton")
        applicationContext.define<TypeA>(scope = "singleton")
        applicationContext.define<TypeB>(scope = "singleton")
        applicationContext.define<TypeC>(scope = "singleton")
        applicationContext.link()

        val components = applicationContext.get<TypeA>()
        Assertions.assertEquals(1, components.count(), "More than one ComponentFactory found")
        val factory = components()
        Assertions.assertNotNull(factory, "No Primary ComponentFactory found")
        Assertions.assertEquals(1, factory!!.count(), "More than one Instance in ComponentFactory found")
        applicationContext.getInstance<TypeA>()

        val listComponents = applicationContext.get<Type>()
        Assertions.assertEquals(3, listComponents.count(), "Not exactly three ComponentFactories found")
        Assertions.assertNotNull(listComponents.count(), "Not exactly three Instances found")

        val allSingleton = applicationContext.getInstance<TypeAll>()
        Assertions.assertNotNull(allSingleton, "SingletonAll not present")
        Assertions.assertEquals(3, allSingleton!!.types.size, "Not exactly 3 Singletons in SingletonAll")
    }

    @Test
    fun `test dependency injection with singleton only with defined names`() {
        val applicationContext = DefaultApplicationContext()
        applicationContext.define<TypeA>("singletonA", "singleton")
        applicationContext.define<TypeB>("singletonB", "singleton")
        applicationContext.define<TypeC>("singletonC", "singleton")
        applicationContext.link()

        val typeComponents = applicationContext.get<TypeA>()
        Assertions.assertEquals(1, typeComponents.count(), "More than one ComponentFactory found")
        val typeFactory = typeComponents()
        Assertions.assertNotNull(typeFactory, "No Primary ComponentFactory found")
        Assertions.assertEquals(1, typeFactory!!.count(), "More than one Instance in ComponentFactory found")
        Assertions.assertNotNull(applicationContext.getInstance<TypeA>(), "Direct Type-Instance is not defined type")

        val nameFactory = applicationContext["singletonA"]
        Assertions.assertNotNull(nameFactory, "No ComponentFactory by name found")
        Assertions.assertEquals(1, nameFactory!!.count(), "More than one Instance in named ComponentFactory found")
        Assertions.assertTrue(nameFactory.invoke() is TypeA, "Named-Instance is not the defined type")
        Assertions.assertTrue(applicationContext.getInstance("singletonA") is TypeA, "Direct Named-Instance is not the defined type")
    }

    @Test
    fun `test dependency injection with prototype only with generated name`() {
        val applicationContext = DefaultApplicationContext()
        applicationContext.define<TypeA>(scope = "prototype")
        applicationContext.define<TypeB>(scope = "prototype")
        applicationContext.define<TypeC>(scope = "prototype")
        applicationContext.define<TypeAll>(scope = "prototype")
        applicationContext.link()

        Assertions.assertNotNull(applicationContext.getInstance<TypeA>())
        Assertions.assertNotNull(applicationContext.getInstance<TypeA>())
        Assertions.assertNotNull(applicationContext.getInstance<TypeA>())

        val components = applicationContext.get<TypeA>()
        Assertions.assertEquals(1, components.count(), "More than one ComponentFactory found")
        val factory = components()
        Assertions.assertNotNull(factory, "No Primary ComponentFactory found")
        Assertions.assertEquals(3, factory!!.count(), "Not exactly three Instances in ComponentFactory found")

        val allSingleton = applicationContext.getInstance<TypeAll>()
        Assertions.assertNotNull(allSingleton, "SingletonAll not present")
        Assertions.assertEquals(9, allSingleton!!.types.size, "Not exactly nine Singletons in SingletonAll")
    }

    @Test
    fun `test dependency injection with prototype only with defined names`() {
        val applicationContext = DefaultApplicationContext()
        applicationContext.define<TypeA>("singletonA", "prototype")
        applicationContext.define<TypeB>("singletonB", "prototype")
        applicationContext.define<TypeC>("singletonC", "prototype")
        applicationContext.link()

        Assertions.assertNotNull(applicationContext.getInstance<TypeA>())
        Assertions.assertNotNull(applicationContext.getInstance<TypeA>())
        Assertions.assertNotNull(applicationContext.getInstance<TypeA>())

        val typeComponents = applicationContext.get<TypeA>()
        Assertions.assertEquals(1, typeComponents.count(), "More than one ComponentFactory found")
        val typeFactory = typeComponents()
        Assertions.assertNotNull(typeFactory, "No Primary ComponentFactory found")
        Assertions.assertEquals(3, typeFactory!!.count(), "Not exactly three Instances in ComponentFactory found")

        val nameFactory = applicationContext["singletonA"]
        Assertions.assertNotNull(nameFactory, "No ComponentFactory by name found")
        Assertions.assertEquals(3, nameFactory!!.count(), "Not exactly three Instances in named ComponentFactory found")
        Assertions.assertTrue(nameFactory.invoke() is TypeA, "Named-Instance is not the defined type")
    }
}

internal interface Type

internal class TypeA(
    val typeB: TypeB,
    val typeC: TypeC
): Type

internal class TypeB: Type

internal class TypeC: Type

internal class TypeAll(
    val types: List<Type>
)
