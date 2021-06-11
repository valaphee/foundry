/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject

import com.valaphee.foundry.inject.context.Component
import com.valaphee.foundry.inject.context.Configuration
import com.valaphee.foundry.inject.context.Controller
import com.valaphee.foundry.inject.context.Provide
import com.valaphee.foundry.inject.context.Repository
import com.valaphee.foundry.inject.context.Service
import com.valaphee.foundry.inject.context.getInstance
import com.valaphee.foundry.inject.processor.ComponentProcessor
import com.valaphee.foundry.inject.processor.ConfigurationProvideProcessor
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SingularityApplicationTest {
    @Test
    fun `test simple components`() {
        val application = TestDefaultApplication()
        application.addClass<ComponentProcessor>()
        application.addClass<ConfigurationProvideProcessor>()
        application.addClass<TestRepository>()
        application.addClass<TestService>()
        application.addClass<TestController>()
        application()

        Assertions.assertNotNull(application.applicationContext.getInstance<TestController>(), "TestController is null")
    }

    @Test
    fun `test components and configurations`() {
        val application = TestDefaultApplication()
        application.addClass<ComponentProcessor>()
        application.addClass<ConfigurationProvideProcessor>()
        application.addClass<TestRepository>()
        application.addClass<TestService>()
        application.addClass<TestController>()
        application.addClass<TestConfiguration>()
        application.addClass<TestComponent>()
        application()

        Assertions.assertNotNull(application.applicationContext.getInstance<TestController>(), "TestController is null")
        Assertions.assertNotNull(application.applicationContext.getInstance<TestComponent>(), "TestComponent is null")
    }
}

internal class TestDefaultApplication : DefaultApplication(setOf("test"))

@Repository
class TestRepository

@Service
class TestService(
    val testRepository: TestRepository
)

@Controller
class TestController(
    val testService: TestService
)

data class TestX(
    val name: String
)

@Configuration
class TestConfiguration {
    @Provide
    fun provideTestX() = TestX("Test")
}

@Component
class TestComponent(
    val testX: TestX
)
