package com.kotlinspring.repository

import com.kotlinspring.entity.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?
    override fun findAll(pageable: Pageable): Page<UserEntity>
//    fun existByUsername(username: String): Boolean
//    @Query("SELECT u FROM UserEntity u WHERE u.id = :id")
//    fun findUserById(@Param("id") id: Long): UserEntity

}