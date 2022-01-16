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

import com.valaphee.foundry.databind.ObservableValueChanged
import com.valaphee.foundry.databind.ScalarChange
import com.valaphee.foundry.databind.converter.IsomorphicConverter
import com.valaphee.foundry.databind.property.InternalProperty

/**
 * @author Kevin Ludwig
 */
class BidirectionalBinding<S : Any, T : Any>(
    source: InternalProperty<S>,
    target: InternalProperty<T>,
    convert: IsomorphicConverter<S, T>
) : BaseBinding<S, T>(source, target, mutableListOf()) {
    private val reverseConvert = convert.reverseConverter()

    init {
        subscriptions.add(source.onChange { runWithDisposeOnFailure { if (target.updateWithEvent(convert(it.oldValue), convert(it.newValue), it)) onChange.publish(ObservableValueChanged(it.oldValue, it.newValue, this, this, listOf(it) + it.trace)) } })
        subscriptions.add(target.onChange {
            runWithDisposeOnFailure {
                val oldValue = reverseConvert(it.oldValue)
                val newValue = reverseConvert(it.newValue)
                if (source.updateWithEvent(oldValue, newValue, it)) onChange.publish(ObservableValueChanged(oldValue, newValue, this, this, listOf(it) + it.trace, ScalarChange))
            }
        })
    }
}
