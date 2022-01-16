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

import com.valaphee.foundry.util.Disposable
import org.apache.logging.log4j.LogManager

/**
 * @author Kevin Ludwig
 */
interface Subscription : Disposable

typealias Subscriptions = MutableList<Subscription>

fun Subscriptions.dispose() {
    forEach {
        try {
            it.dispose()
        } catch (ex: Exception) {
            log.warn("Cancelling subscription failed", ex)
        }
    }
    clear()
}

private val log = LogManager.getLogger(Subscription::class.java)
