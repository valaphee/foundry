/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.event

/**
 * @author Kevin Ludwig
 */
sealed class CallbackResult

/**
 * @author Kevin Ludwig
 */
object KeepSubscription : CallbackResult()

/**
 * @author Kevin Ludwig
 */
object DisposeSubscription : CallbackResult()
