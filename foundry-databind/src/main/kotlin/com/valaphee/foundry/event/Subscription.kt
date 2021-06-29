/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
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
