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

package com.valaphee.foundry.databind.binding

import com.valaphee.foundry.databind.converter.toConverter
import com.valaphee.foundry.databind.property.toInternalProperty
import com.valaphee.foundry.databind.value.ObservableValue

fun ObservableValue<String>.bindIsEmpty(): Binding<Boolean> {
    val convert = { value: String -> value.isEmpty() }.toConverter()
    return UnidirectionalBinding(this, convert(this.value).toInternalProperty(), convert)
}

fun ObservableValue<String>.bindIsBlank(): Binding<Boolean> {
    val convert = { value: String -> value.isBlank() }.toConverter()
    return UnidirectionalBinding(this, convert(this.value).toInternalProperty(), convert)
}

infix fun ObservableValue<String>.bindPlusWith(b: ObservableValue<String>) = ComputedDualBinding(this, b) { a, b -> a + b }

infix fun ObservableValue<String>.bindEqualsWith(b: ObservableValue<String>) = ComputedDualBinding(this, b) { a, b -> a == b }

infix fun ObservableValue<String>.bindEqualsIgnoreCase(b: ObservableValue<String>) = ComputedDualBinding(this, b) { a, b -> a.equals(b, ignoreCase = true) }

fun ObservableValue<String>.length() = ComputedBinding(this) { it.length }
