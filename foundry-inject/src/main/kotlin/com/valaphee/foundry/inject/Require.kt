/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject

import java.lang.annotation.Inherited

/**
 * @author Kevin Ludwig
 */
@Retention
@Inherited
@Target(AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS)
annotation class Require(
    vararg val value: String
)
