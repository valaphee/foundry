/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.binding

import com.valaphee.foundry.databind.value.ObservableValue

fun ObservableValue<Float>.bindNegate() = ComputedBinding(this) { -it }

infix fun ObservableValue<Float>.bindPlusWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a + b.toFloat() }

infix fun ObservableValue<Float>.bindMinusWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a - b.toFloat() }

infix fun ObservableValue<Float>.bindTimesWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a * b.toFloat() }

infix fun ObservableValue<Float>.bindDivWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a / b.toFloat() }

infix fun ObservableValue<Float>.bindEqualsWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a == b.toFloat() }

infix fun ObservableValue<Float>.bindGreaterThanWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a > b.toFloat() }

infix fun ObservableValue<Float>.bindLessThanWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a < b.toFloat() }

infix fun ObservableValue<Float>.bindGreaterThanOrEqualToWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a >= b.toFloat() }

infix fun ObservableValue<Float>.bindLessThanOrEqualToWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a <= b.toFloat() }

fun ObservableValue<Float>.bindToString() = ComputedBinding(this) { it.toString() }
