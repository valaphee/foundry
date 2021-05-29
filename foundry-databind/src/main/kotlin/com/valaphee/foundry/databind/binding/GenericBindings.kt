/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.databind.binding

import com.valaphee.foundry.databind.collection.ListBindingDecorator
import com.valaphee.foundry.databind.collection.ObservableList
import com.valaphee.foundry.databind.collection.ObservableListBinding
import com.valaphee.foundry.databind.value.ObservableValue

fun <S : Any, T : Any> ObservableValue<S>.bindTransform(transform: (S) -> T): Binding<T> = ComputedBinding(this, transform)

fun <S : Any, T : Any> ObservableList<S>.bindMap(transform: (S) -> T): ObservableListBinding<T> = ListBindingDecorator(ListBinding(this, transform))
