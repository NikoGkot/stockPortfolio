package com.kotlinspring.repository

import com.kotlinspring.entity.Role
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName (name:String): Role?

    fun findAllByName(name: String): List<Role>
}