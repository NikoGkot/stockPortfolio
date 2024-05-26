package com.kotlinspring.service

import com.kotlinspring.entity.UserEntity
import com.kotlinspring.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun findAll(): List<UserEntity> = userRepository.findAll()

    fun createUser(userEntity: UserEntity): UserEntity? {
        val found = userRepository.findByUsername(userEntity.username)

        return if (found == null) {
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