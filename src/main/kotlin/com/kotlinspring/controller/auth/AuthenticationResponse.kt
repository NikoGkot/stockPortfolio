package com.kotlinspring.controller.auth

data class AuthenticationResponse (
    val accessToken: String,
    val refreshToken: String,
    val redirectUrl: String
){

}
