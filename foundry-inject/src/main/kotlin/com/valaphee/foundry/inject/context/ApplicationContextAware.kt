/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

import com.valaphee.foundry.inject.Aware

/**
 * @author Kevin Ludwig
 */
interface ApplicationContextAware: Aware {
    var applicationContext: ApplicationContext
}
