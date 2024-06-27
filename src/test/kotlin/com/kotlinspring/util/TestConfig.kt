// src/test/kotlin/com/kotlinspring/config/TestConfig.kt
package com.kotlinspring.util

import com.kotlinspring.entity.Role
import com.kotlinspring.entity.UserEntity
import com.kotlinspring.service.CustomUserDetailsService
import com.kotlinspring.service.TokenService
import io.mockk.every
import io.mockk.mockk
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import java.util.*

//@TestConfiguration
//class TestConfig {
//
//    @Bean
//    fun userDetailsService(): CustomUserDetailsService {
//        val mockUserDetailsService = mockk<CustomUserDetailsService>()
//
//        val userRoles = setOf(Role(1, "ROLE_USER"))
//        val userEntity = UserEntity(1, "testuser", "password", userRoles)
//
//        every { mockUserDetailsService.loadUserByUsername("testuser") } returns userEntity.toUserDetails()
//
//        return mockUserDetailsService
//    }
//
//    @Bean
//    fun tokenService(): TokenService {
//        val mockTokenService = mockk<TokenService>()
//
//        every { mockTokenService.extractUsername(any()) } returns "testuser"
//        every { mockTokenService.isValid(any(), any()) } returns true
//
//        val userDetails: UserDetails = User.withUsername("testuser")
//            .password("password")
//            .authorities("ROLE_USER")
//            .build()
//        val expirationDate = Date(System.currentTimeMillis() + 1000 * 60 * 60) // 1 hour
//
//        every { mockTokenService.generate(userDetails, expirationDate, any()) } returns "mock-jwt-token"
//
//        return mockTokenService
//    }
//
//    @Bean
//    fun authenticationProvider(userDetailsService: CustomUserDetailsService): AuthenticationProvider {
//        val authProvider = DaoAuthenticationProvider()
//        authProvider.setUserDetailsService(userDetailsService)
//        return authProvider
//    }
//
//    @Bean
//    fun jwtToken(tokenService: TokenService): String {
//        val userDetails: UserDetails = User.withUsername("testuser")
//            .password("password")
//            .authorities("ROLE_USER")
//            .build()
//        val expirationDate = Date(System.currentTimeMillis() + 1000 * 60 * 60) // 1 hour
//        return tokenService.generate(userDetails, expirationDate)
//    }
//
//    private fun UserEntity.toUserDetails(): UserDetails {
//        return User.withUsername(this.username)
//            .password(this.password)
//            .authorities(this.roles.map { SimpleGrantedAuthority(it?.name ?: "ROLE_USER") })
//            .build()
//    }
//}
