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

import com.valaphee.foundry.databind.value.ObservableValue

fun ObservableValue<Int>.bindNegate() = ComputedBinding(this) { -it }

infix fun ObservableValue<Int>.bindPlusWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a + b.toInt() }

infix fun ObservableValue<Int>.bindMinusWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a - b.toInt() }

infix fun ObservableValue<Int>.bindTimesWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a * b.toInt() }

infix fun ObservableValue<Int>.bindDivWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a / b.toInt() }

infix fun ObservableValue<Int>.bindEqualsWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a == b.toInt() }

infix fun ObservableValue<Int>.bindGreaterThanWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a > b.toInt() }

infix fun ObservableValue<Int>.bindLessThanWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a < b.toInt() }

infix fun ObservableValue<Int>.bindGreaterThanOrEqualToWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a >= b.toInt() }

infix fun ObservableValue<Int>.bindLessThanOrEqualToWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a <= b.toInt() }

fun ObservableValue<Int>.bindToString() = ComputedBinding(this) { it.toString() }
