/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.property

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author Kevin Ludwig
 */
interface PropertyDelegate<T : Any> : Property<T>, ReadWriteProperty<Any?, T>

/**
 * @author Kevin Ludwig
 */
class DefaultPropertyDelegate<T : Any>(
    private val property: Property<T>
) : PropertyDelegate<T>, Property<T> by property {
    override fun getValue(thisRef: Any?, property: KProperty<*>) = this.property.value

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.property.value = value
    }
}
