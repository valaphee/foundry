/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject

import com.valaphee.foundry.inject.context.ComponentCircularDependencyException
import com.valaphee.foundry.inject.context.DefaultApplicationContext
import com.valaphee.foundry.inject.context.define
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CircularDependencyTest {
    @Test
    fun `test circular dependency exception`() {
        val applicationContext = DefaultApplicationContext()
        applicationContext.define<A>()
        applicationContext.define<B>()
        applicationContext.define<C>()
        applicationContext.define<D>()
        assertThrows<ComponentCircularDependencyException> { applicationContext.link() }
    }
}

internal class A(
    val b: B
)

internal class B(
    val c: C
)

internal class C(
    val d: D
)

internal class D(
    val a: A
)
