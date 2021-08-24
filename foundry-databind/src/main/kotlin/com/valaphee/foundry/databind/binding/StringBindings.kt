/*
 * MIT License
 *
 * Copyright (c) 2021, Valaphee.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
