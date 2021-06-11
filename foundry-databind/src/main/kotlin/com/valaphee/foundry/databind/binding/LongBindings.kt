/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

@file:JvmName("LongBindings")

package com.valaphee.foundry.databind.binding

import com.valaphee.foundry.databind.value.ObservableValue

fun ObservableValue<Long>.bindNegate() = ComputedBinding(this) { -it }

infix fun ObservableValue<Long>.bindPlusWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue + thatValue.toLong() }

infix fun ObservableValue<Long>.bindMinusWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue - thatValue.toLong() }

infix fun ObservableValue<Long>.bindTimesWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue * thatValue.toLong() }

infix fun ObservableValue<Long>.bindDivWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue / thatValue.toLong() }

infix fun ObservableValue<Long>.bindEqualsWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue == thatValue.toLong() }

infix fun ObservableValue<Long>.bindGreaterThanWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue > thatValue.toLong() }

infix fun ObservableValue<Long>.bindLessThanWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue < thatValue.toLong() }

infix fun ObservableValue<Long>.bindGreaterThanOrEqualToWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue >= thatValue.toLong() }

infix fun ObservableValue<Long>.bindLessThanOrEqualToWith(that: ObservableValue<Number>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue <= thatValue.toLong() }

fun ObservableValue<Long>.bindToString() = ComputedBinding(this) { it.toString() }
