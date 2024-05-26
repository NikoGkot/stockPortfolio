package com.kotlinspring.controller.auth

data class AuthenticationRequest(
    val username: String,
    val password: String
)