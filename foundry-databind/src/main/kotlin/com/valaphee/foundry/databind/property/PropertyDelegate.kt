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
