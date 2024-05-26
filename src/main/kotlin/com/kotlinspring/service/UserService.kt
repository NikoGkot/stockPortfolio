package com.kotlinspring.service

import com.kotlinspring.controller.user.UserRequest
import com.kotlinspring.entity.UserEntity
import com.kotlinspring.repository.RoleRepository
import com.kotlinspring.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import javax.management.relation.RoleNotFoundException

@Service
class UserService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun findAll(): List<UserEntity> = userRepository.findAll()

    fun createUser(userRequest: UserRequest): UserEntity? {
        val found = userRepository.findByUsername(userRequest.username)

        return if (found == null) {
            // Fetch the role from the database
            val role = roleRepository.findByName("ROLE_${userRequest.roles}") ?: throw RoleNotFoundException("Role not found")

            // Create the UserEntity
            val userEntity = UserEntity(
                username = userRequest.username,
                password = passwordEncoder.encode(userRequest.password),
                roles = setOf(role)
            )

            // Save the user to the database
            userRepository.save(userEntity)
            userEntity
        } else null

    }

    fun findById(id: Long): UserEntity? {
        return userRepository.findById(id).orElse(null)
    }

    fun findByUsername(username: String): UserEntity? =
        userRepository.findByUsername(username)

    fun deleteById(id: Long) {
        userRepository.deleteById(id)
    }

}