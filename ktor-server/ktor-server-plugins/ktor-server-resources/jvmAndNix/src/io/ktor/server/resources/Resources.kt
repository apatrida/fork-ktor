/*
 * Copyright 2014-2021 JetBrains s.r.o and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package io.ktor.server.resources

import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.util.*
import io.ktor.resources.Resources as ResourcesCore

/**
 * Adds support for type-safe routing using [ResourcesCore].
 *
 * Example:
 * ```kotlin
 * @Serializable
 * @Resource("/users")
 * data class Users {
 *   @Serializable
 *   @Resource("/{id}")
 *   data class ById(val parent: Users = Users(), val id: Long)
 *
 *   @Serializable
 *   @Resource("/add")
 *   data class Add(val parent: Users = Users(), val name: String)
 * }
 *
 * routing {
 *   get<Users.ById> { userById ->
 *     val userId: Long = userById.id
 *   }
 *   post<Users.Add> { addUser ->
 *     val userName: String = addUser.name
 *   }
 * }
 *
 * // client-side
 * val newUserId = client.post(Users.Add("new_user"))
 * val addedUser = client.get(Users.ById(newUserId))
 * ```
 *
 * @see Resource
 */
public object Resources : ApplicationPlugin<Application, ResourcesCore.Configuration, ResourcesCore> {

    override val key: AttributeKey<ResourcesCore> = AttributeKey("Resources")

    override fun install(pipeline: Application, configure: ResourcesCore.Configuration.() -> Unit): ResourcesCore {
        val configuration = ResourcesCore.Configuration().apply(configure)
        return ResourcesCore(configuration)
    }
}

/**
 * Constructs a URL for [resource].
 *
 * The class of the [resource] instance **must** be annotated with [Resource].
 */
public inline fun <reified T : Any> Application.href(resource: T): String {
    return href(plugin(Resources).resourcesFormat, resource)
}

/**
 * Constructs a URL for [resource].
 *
 * The class of the [resource] instance **must** be annotated with [Resource].
 */
public inline fun <reified T : Any> Application.href(resource: T, urlBuilder: URLBuilder) {
    href(plugin(Resources).resourcesFormat, resource, urlBuilder)
}
