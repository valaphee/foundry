/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
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

infix fun ObservableValue<String>.bindPlusWith(that: ObservableValue<String>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue + thatValue }

infix fun ObservableValue<String>.bindEqualsWith(that: ObservableValue<String>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue == thatValue }

infix fun ObservableValue<String>.bindEqualsIgnoreCase(that: ObservableValue<String>) = ComputedDualBinding(this, that) { thisValue, thatValue -> thisValue.equals(thatValue, ignoreCase = true) }

fun ObservableValue<String>.length() = ComputedBinding(this) { it.length }
