/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.value

import com.valaphee.foundry.databind.ObservableValueChanged
import com.valaphee.foundry.event.Subscription

/**
 * @author Kevin Ludwig
 */
interface ObservableValue<out T : Any> : Value<T> {
    fun onChange(callback: (ObservableValueChanged<T>) -> Unit): Subscription
}
