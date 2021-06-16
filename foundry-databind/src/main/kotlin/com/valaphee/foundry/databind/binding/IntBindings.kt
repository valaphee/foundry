/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
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
