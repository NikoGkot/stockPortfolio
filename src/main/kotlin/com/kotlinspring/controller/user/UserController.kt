package com.kotlinspring.controller.user

import com.kotlinspring.entity.UserEntity
import com.kotlinspring.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService

) {
    @PostMapping
    fun create(@RequestBody userRequest: UserRequest): UserResponse {
        return userService.createUser(
//            userEntity = userRequest.toModel()
            userRequest
        )?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create a user.")

    }

    @GetMapping
    fun listAll(): List<UserResponse> =
        userService.findAll()
            .map { it.toResponse() }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): UserResponse {
        val userEntity = userService.findById(id)
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot find a user.")
        return userEntity.toResponse()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable id: Long) {
        val success = userService.deleteById(id)
    }

    private fun UserEntity.toResponse(): UserResponse =
        UserResponse(
            id = this.id,
            username = this.username
        )

}