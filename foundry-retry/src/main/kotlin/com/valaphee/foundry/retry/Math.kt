/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

@file:JvmName("Math")

package com.valaphee.foundry.retry

infix fun Long.smul(other: Long) = if (this == 0L || other <= Long.MAX_VALUE / this) this * other else Long.MAX_VALUE

infix fun Long.sadd(other: Long) = if (this == 0L || other <= Long.MAX_VALUE - this) this + other else Long.MAX_VALUE

fun Int.exp2() = if (this < Long.SIZE_BITS - 1) 1L shl this else Long.MAX_VALUE
