/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.event

/**
 * @author Kevin Ludwig
 */
sealed class SubmitResult<out T : Any>(
    val successful: Boolean
) {
    abstract operator fun component1(): T
}

/**
 * @author Kevin Ludwig
 */
object NoProcessorSubmitResult : SubmitResult<Nothing>(false) {
    override fun component1() = error("No processor found for the given event and result type")
}

/**
 * @author Kevin Ludwig
 */
class SubmitResultValue<out T : Any>(
    val value: T
) : SubmitResult<T>(true) {
    override fun component1() = value
}

/**
 * @author Kevin Ludwig
 */
class SubmitResultError(
    val exception: Exception
) : SubmitResult<Nothing>(false) {
    override fun component1() = throw exception
}
