/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.binding

import com.valaphee.foundry.databind.value.ObservableValue

fun ObservableValue<Double>.bindNegate() = ComputedBinding(this) { -it }

infix fun ObservableValue<Double>.bindPlusWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a + b.toDouble() }

infix fun ObservableValue<Double>.bindMinusWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a - b.toDouble() }

infix fun ObservableValue<Double>.bindTimesWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a * b.toDouble() }

infix fun ObservableValue<Double>.bindDivWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a / b.toDouble() }

infix fun ObservableValue<Double>.bindEqualsWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a == b.toDouble() }

infix fun ObservableValue<Double>.bindGreaterThanWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a > b.toDouble() }

infix fun ObservableValue<Double>.bindLessThanWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a < b.toDouble() }

infix fun ObservableValue<Double>.bindGreaterThanOrEqualToWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a >= b.toDouble() }

infix fun ObservableValue<Double>.bindLessThanOrEqualToWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a <= b.toDouble() }

fun ObservableValue<Double>.bindToString() = ComputedBinding(this) { it.toString() }
