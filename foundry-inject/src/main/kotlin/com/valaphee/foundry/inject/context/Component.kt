/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

import com.valaphee.foundry.inject.Indexed

/**
 * @author Kevin Ludwig
 */
@Indexed
@Retention
@Target(AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS)
annotation class Component
