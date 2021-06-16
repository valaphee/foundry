/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.binding

import com.valaphee.foundry.databind.value.ObservableValue

fun ObservableValue<Int>.bindNegate() = ComputedBinding(this) { -it }

infix fun ObservableValue<Int>.bindPlusWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { a, b -> a + b.toInt() }

infix fun ObservableValue<Int>.bindMinusWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { a, b -> a - b.toInt() }

infix fun ObservableValue<Int>.bindTimesWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { a, b -> a * b.toInt() }

infix fun ObservableValue<Int>.bindDivWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { a, b -> a / b.toInt() }

infix fun ObservableValue<Int>.bindEqualsWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { a, b -> a == b.toInt() }

infix fun ObservableValue<Int>.bindGreaterThanWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { a, b -> a > b.toInt() }

infix fun ObservableValue<Int>.bindLessThanWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { a, b -> a < b.toInt() }

infix fun ObservableValue<Int>.bindGreaterThanOrEqualToWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { a, b -> a >= b.toInt() }

infix fun ObservableValue<Int>.bindLessThanOrEqualToWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { a, b -> a <= b.toInt() }

fun ObservableValue<Int>.bindToString() = ComputedBinding(this) { it.toString() }
