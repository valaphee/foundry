/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.binding

import com.valaphee.foundry.databind.value.ObservableValue

fun ObservableValue<Double>.bindNegate() = ComputedBinding(this) { -it }

infix fun ObservableValue<Double>.bindPlusWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue + thatValue.toDouble() }

infix fun ObservableValue<Double>.bindMinusWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue - thatValue.toDouble() }

infix fun ObservableValue<Double>.bindTimesWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue * thatValue.toDouble() }

infix fun ObservableValue<Double>.bindDivWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue / thatValue.toDouble() }

infix fun ObservableValue<Double>.bindEqualsWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue == thatValue.toDouble() }

infix fun ObservableValue<Double>.bindGreaterThanWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue > thatValue.toDouble() }

infix fun ObservableValue<Double>.bindLessThanWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue < thatValue.toDouble() }

infix fun ObservableValue<Double>.bindGreaterThanOrEqualToWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue >= thatValue.toDouble() }

infix fun ObservableValue<Double>.bindLessThanOrEqualToWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue <= thatValue.toDouble() }

fun ObservableValue<Double>.bindToString() = ComputedBinding(this) { it.toString() }
