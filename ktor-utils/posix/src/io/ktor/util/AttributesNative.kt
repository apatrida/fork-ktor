/*
 * Copyright 2014-2019 JetBrains s.r.o and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package io.ktor.util

import io.ktor.util.collections.*

/**
 * Create native specific attributes instance.
 */
public actual fun Attributes(concurrent: Boolean): Attributes = AttributesNative()

private class AttributesNative : Attributes {
    private val map = ConcurrentMap<AttributeKey<*>, Any>()

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> getOrNull(key: AttributeKey<T>): T? = map[key] as T?

    override operator fun contains(key: AttributeKey<*>): Boolean = map.containsKey(key)

    override fun <T : Any> put(key: AttributeKey<T>, value: T) {
        map[key] = value
    }

    override fun <T : Any> remove(key: AttributeKey<T>) {
        map.remove(key)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> computeIfAbsent(key: AttributeKey<T>, block: () -> T): T {
        return map.computeIfAbsent(key, block) as T
    }

    override val allKeys: List<AttributeKey<*>>
        get() = map.keys.toList()
}
