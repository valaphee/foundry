/*
 * Copyright (c) 2021-2022, Valaphee.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
    val error: Exception
) : SubmitResult<Nothing>(false) {
    override fun component1() = throw error
}
