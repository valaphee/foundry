/*
 * MIT License
 *
 * Copyright (c) 2021, Valaphee.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.valaphee.foundry.event

import com.valaphee.foundry.databind.toAtom
import com.valaphee.foundry.util.DisposeState
import com.valaphee.foundry.util.DisposedByException
import com.valaphee.foundry.util.NotDisposed
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import org.apache.logging.log4j.LogManager
import kotlin.reflect.KClass

/**
 * @author Kevin Ludwig
 */
interface EventBus {
    fun fetchSubscribersOf(scope: EventScope = GlobalScope, key: String): Iterable<Subscription>

    fun <T : Event> subscribeTo(scope: EventScope = GlobalScope, key: String, callback: (T) -> CallbackResult): Subscription

    fun publish(event: Event, scope: EventScope = GlobalScope)

    fun cancelScope(scope: EventScope)

    fun close()
}

fun eventBus(): EventBus = DefaultEventBus()

inline fun <reified T : Event> EventBus.subscribeToWithoutResult(eventScope: EventScope = GlobalScope, noinline callback: (T) -> Unit) = subscribeTo<T>(eventScope, checkNotNull(T::class.simpleName)) {
    callback(it)
    KeepSubscription
}

fun <T : Event> EventBus.subscribeToWithoutResult(eventScope: EventScope = GlobalScope, `class`: KClass<T>, subscription: Subscription) = subscribeTo<T>(eventScope, checkNotNull(`class`.simpleName)) {
    @Suppress("UNCHECKED_CAST")
    (subscription as DefaultEventBus.DefaultSubscription<T>).callback(it)
    KeepSubscription
}

inline fun <reified T : Event> EventBus.subscribeTo(eventScope: EventScope = GlobalScope, noinline callback: (T) -> CallbackResult) = subscribeTo(eventScope, checkNotNull(T::class.simpleName), callback)

fun <T : Event> EventBus.subscribeTo(eventScope: EventScope = GlobalScope, `class`: KClass<T>, subscription: Subscription) = subscribeTo(eventScope, checkNotNull(`class`.simpleName), (subscription as DefaultEventBus.DefaultSubscription<*>).callback)

/**
 * @author Kevin Ludwig
 */
class DefaultEventBus : EventBus {
    @Volatile
    private var closed = false

    @Volatile
    private var subscribers = persistentMapOf<SubscriberKey, PersistentList<DefaultSubscription<*>>>().toAtom()

    override fun fetchSubscribersOf(scope: EventScope, key: String): Iterable<Subscription> = subscribers.get().getOrElse(SubscriberKey(scope, key)) { listOf() }

    override fun <T : Event> subscribeTo(scope: EventScope, key: String, callback: (T) -> CallbackResult): Subscription = whenNotClosed {
        try {
            /*log.trace("Subscribing to $key with scope $scope")*/
            val subscription = DefaultSubscription(scope, key, callback)
            val subscriberKey = SubscriberKey(scope, key)
            subscribers.transform { it.put(subscriberKey, it.getOrElse(subscriberKey) { persistentListOf() }.add(subscription)) }
            subscription
        } catch (ex: Exception) {
            log.warn("Failed to subscribe to event key $key with scope $scope", ex)
            throw ex
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun publish(event: Event, scope: EventScope): Unit = whenNotClosed {
        /*log.trace("Publishing event with key ${event.key} and scope $scope")*/
        subscribers.get()[SubscriberKey(scope, event.key)]?.let {
            it.forEach {
                try {
                    if ((it.callback as (Event) -> CallbackResult).invoke(event) is DisposeSubscription) it.dispose()
                } catch (ex: Exception) {
                    log.warn("Cancelling failed subscription $it", ex)
                    try {
                        it.dispose(DisposedByException(ex))
                    } catch (ex: Exception) {
                        log.warn("Failed to cancel subscription $it", ex)
                    }
                }
            }
        }
    }

    override fun cancelScope(scope: EventScope): Unit = whenNotClosed {
        /*log.trace("Cancelling scope $scope")*/
        subscribers.get().filter { it.key.scope == scope }.flatMap { it.value }.forEach {
            try {
                it.dispose()
            } catch (ex: Exception) {
                log.warn("Cancelling subscription failed while cancelling scope", ex)
            }
        }
    }

    override fun close() {
        closed = true
        subscribers.transform {
            it.forEach { (_, subscriptions) -> subscriptions.forEach { it.dispose() } }
            persistentMapOf()
        }
    }

    private fun <T> whenNotClosed(run: () -> T) = if (closed) error("Already closed") else run()

    private class SubscriberKey(
        val scope: EventScope,
        val key: String
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as SubscriberKey

            if (scope != other.scope) return false
            if (key != other.key) return false

            return true
        }

        override fun hashCode(): Int {
            var result = scope.hashCode()
            result = 31 * result + key.hashCode()
            return result
        }
    }

    inner class DefaultSubscription<in T : Event>(
        val scope: EventScope,
        val key: String,
        val callback: (T) -> CallbackResult
    ) : Subscription {
        override var disposeState: DisposeState = NotDisposed
            private set

        @Suppress("UNCHECKED_CAST")
        override fun dispose(disposeState: DisposeState) {
            try {
                /*log.trace("Cancelling subscription with scope $scope and key $key")*/
                val key = SubscriberKey(scope, key)
                this.disposeState = disposeState
                subscribers.transform {
                    var newSubscriptions = it
                    it[key]?.let {
                        val newSubscriptions0 = it.remove(this as DefaultSubscription<*>)
                        newSubscriptions = if (newSubscriptions0.isEmpty()) newSubscriptions.remove(key) else newSubscriptions.put(key, newSubscriptions0)
                        newSubscriptions
                    } ?: newSubscriptions
                }
            } catch (ex: Exception) {
                log.warn("Cancelling subscription failed", ex)
                throw ex
            }
        }
    }

    companion object {
        private val log = LogManager.getLogger(DefaultEventBus::class.java)
    }
}

/**
 * @author Kevin Ludwig
 */
interface UnscopedEventBus : EventBus {
    fun fetchSubscribers(): Iterable<Subscription>

    override fun fetchSubscribersOf(scope: EventScope, key: String): Iterable<Subscription> = fetchSubscribers()

    fun <T : Event> subscribe(callback: (T) -> CallbackResult): Subscription

    override fun <T : Event> subscribeTo(scope: EventScope, key: String, callback: (T) -> CallbackResult): Subscription = subscribe(callback)

    fun publish(event: Event)

    override fun publish(event: Event, scope: EventScope) = publish(event)

    fun cancel()

    override fun cancelScope(scope: EventScope) = cancel()

    override fun close() = cancel()
}

fun unscopedEventBus(): UnscopedEventBus = DefaultUnscopedEventBus()

inline fun <reified T : Event> UnscopedEventBus.subscribeWithoutResult(noinline callback: (T) -> Unit) = subscribe<T> {
    callback(it)
    KeepSubscription
}

/**
 * @author Kevin Ludwig
 */
class DefaultUnscopedEventBus : UnscopedEventBus {
    @Volatile
    private var subscribers = persistentListOf<DefaultGlobalSubscription<*>>().toAtom()

    override fun fetchSubscribers(): Iterable<Subscription> = subscribers.get()

    override fun <T : Event> subscribe(callback: (T) -> CallbackResult): Subscription {
        try {
            /*log.trace("Subscribing to $key with scope $scope")*/
            val subscription = DefaultGlobalSubscription(callback)
            subscribers.transform { it.add(subscription) }
            return subscription
        } catch (ex: Exception) {
            log.warn("Failed to subscribe to event", ex)
            throw ex
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun publish(event: Event) {
        /*log.trace("Publishing event with key ${event.key} and scope $scope")*/
        subscribers.get().let {
            it.forEach {
                try {
                    if ((it.callback as (Event) -> CallbackResult).invoke(event) is DisposeSubscription) it.dispose()
                } catch (ex: Exception) {
                    log.warn("Cancelling failed subscription $it", ex)
                    try {
                        it.dispose(DisposedByException(ex))
                    } catch (ex: Exception) {
                        log.warn("Failed to cancel subscription $it", ex)
                    }
                }
            }
        }
    }

    override fun cancel() {
        /*log.trace("Cancelling scope $scope")*/
        subscribers.get().forEach {
            try {
                it.dispose()
            } catch (ex: Exception) {
                log.warn("Cancelling subscription failed while cancelling scope", ex)
            }
        }
    }

    override fun close() {
        subscribers.transform {
            it.forEach { it.dispose() }
            persistentListOf()
        }
    }

    private class SubscriberKey(
        val scope: EventScope,
        val key: String
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as SubscriberKey

            if (scope != other.scope) return false
            if (key != other.key) return false

            return true
        }

        override fun hashCode(): Int {
            var result = scope.hashCode()
            result = 31 * result + key.hashCode()
            return result
        }
    }

    private inner class DefaultGlobalSubscription<in T : Event>(
        val callback: (T) -> CallbackResult
    ) : Subscription {
        override var disposeState: DisposeState = NotDisposed
            private set

        @Suppress("UNCHECKED_CAST")
        override fun dispose(disposeState: DisposeState) {
            try {
                /*log.trace("Cancelling subscription with scope $scope and key $key")*/
                this.disposeState = disposeState
                subscribers.transform { it.remove(this as DefaultGlobalSubscription<*>) }
            } catch (ex: Exception) {
                log.warn("Cancelling subscription failed", ex)
                throw ex
            }
        }
    }

    companion object {
        private val log = LogManager.getLogger(DefaultUnscopedEventBus::class.java)
    }
}
