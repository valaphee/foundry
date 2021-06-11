/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

@file:JvmName("FloatBindings")

package com.valaphee.foundry.databind.binding

import com.valaphee.foundry.databind.value.ObservableValue

fun ObservableValue<Float>.bindNegate() = ComputedBinding(this) { -it }

infix fun ObservableValue<Float>.bindPlusWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue + thatValue.toFloat() }

infix fun ObservableValue<Float>.bindMinusWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue - thatValue.toFloat() }

infix fun ObservableValue<Float>.bindTimesWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue * thatValue.toFloat() }

infix fun ObservableValue<Float>.bindDivWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue / thatValue.toFloat() }

infix fun ObservableValue<Float>.bindEqualsWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue == thatValue.toFloat() }

infix fun ObservableValue<Float>.bindGreaterThanWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue > thatValue.toFloat() }

infix fun ObservableValue<Float>.bindLessThanWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue < thatValue.toFloat() }

infix fun ObservableValue<Float>.bindGreaterThanOrEqualToWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue >= thatValue.toFloat() }

infix fun ObservableValue<Float>.bindLessThanOrEqualToWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue <= thatValue.toFloat() }

fun ObservableValue<Float>.bindToString() = ComputedBinding(this) { it.toString() }
