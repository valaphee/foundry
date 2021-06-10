/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.inject.context

import java.util.LinkedList

/**
 * @author Kevin Ludwig
 */
class Key<T>(
    val targetClass: Class<T>,
    val parentClasses: Map<Class<in T>, Int>
) : Iterable<Class<in T>> {
    companion object {
        fun <T> create(targetClass: Class<T>) = Key(targetClass, targetClass.parentClasses())

        fun <T> Class<T>.key() = create(this)

        @Suppress("UNCHECKED_CAST")
        private fun <T> Class<T>.parentClasses(): Map<Class<in T>, Int> {
            val map = mutableMapOf<Class<in T>, Int>()
            val queue = LinkedList<Pair<Class<in T>, Int>>()
            queue.add(this to 0)
            while (queue.isNotEmpty()) {
                val entry = queue.pop()
                map[entry.first] = entry.second
                entry.first.superclass?.takeUnless { it in map }?.also { queue.add(it to entry.second + 1) }
                queue.addAll(entry.first.interfaces.filterNot { it in map }.map { it as Class<in T> to entry.second + 1 })
            }
            return map
        }
    }

    override fun iterator(): Iterator<Class<in T>> = parentClasses.entries.sortedBy { it.value }.map { it.key }.iterator()
}
