/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.binding

import com.valaphee.foundry.databind.value.ObservableValue

fun ObservableValue<Long>.bindNegate() = ComputedBinding(this) { -it }

infix fun ObservableValue<Long>.bindPlusWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a + b.toLong() }

infix fun ObservableValue<Long>.bindMinusWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a - b.toLong() }

infix fun ObservableValue<Long>.bindTimesWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a * b.toLong() }

infix fun ObservableValue<Long>.bindDivWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a / b.toLong() }

infix fun ObservableValue<Long>.bindEqualsWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a == b.toLong() }

infix fun ObservableValue<Long>.bindGreaterThanWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a > b.toLong() }

infix fun ObservableValue<Long>.bindLessThanWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a < b.toLong() }

infix fun ObservableValue<Long>.bindGreaterThanOrEqualToWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a >= b.toLong() }

infix fun ObservableValue<Long>.bindLessThanOrEqualToWith(b: ObservableValue<Number>) = ComputedDualBinding(this, b) { a, b -> a <= b.toLong() }

fun ObservableValue<Long>.bindToString() = ComputedBinding(this) { it.toString() }
