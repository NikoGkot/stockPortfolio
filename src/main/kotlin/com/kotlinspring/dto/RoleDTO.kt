package com.kotlinspring.dto

import com.kotlinspring.entity.Role


data class RoleDTO(
    val id: Long,
    val name: String
)

fun Role.toDTO(): RoleDTO {
    return RoleDTO(
        id = this.id,
        name = this.name
    )
}
