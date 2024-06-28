package com.kotlinspring.controller.user

import com.kotlinspring.entity.Role
import com.kotlinspring.entity.UserEntity

data class UserRequest(
    val username: String,
    val password: String,
    val roles: String
)
fun UserRequest.toModel(): UserEntity {
    return UserEntity(
        username = this.username,
        password = this.password,
        roles = setOf(Role(id = 1L, name = "USER"))
    )
}
