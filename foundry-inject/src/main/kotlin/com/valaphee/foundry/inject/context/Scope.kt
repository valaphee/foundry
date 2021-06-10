/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

import java.lang.annotation.Inherited

/**
 * @author Kevin Ludwig
 */
@Retention
@Inherited
@Target(AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
annotation class Scope(
    val value: String = "singleton"
)
