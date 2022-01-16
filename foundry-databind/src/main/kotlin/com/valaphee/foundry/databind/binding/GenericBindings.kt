/*
 * Copyright (c) 2021-2022, Valaphee.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.valaphee.foundry.databind.binding

import com.valaphee.foundry.databind.collection.ListBindingDecorator
import com.valaphee.foundry.databind.collection.ObservableList
import com.valaphee.foundry.databind.collection.ObservableListBinding
import com.valaphee.foundry.databind.value.ObservableValue

fun <S : Any, T : Any> ObservableValue<S>.bindTransform(transform: (S) -> T): Binding<T> = ComputedBinding(this, transform)

fun <S : Any, T : Any> ObservableList<S>.bindMap(transform: (S) -> T): ObservableListBinding<T> = ListBindingDecorator(ListBinding(this, transform))
