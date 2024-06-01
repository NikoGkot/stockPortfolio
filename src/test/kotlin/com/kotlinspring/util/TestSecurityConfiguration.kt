// src/test/kotlin/com/kotlinspring/config/TestSecurityConfiguration.kt
package com.kotlinspring.util
import com.kotlinspring.config.JwtAuthenticationFilter
import com.kotlinspring.service.CustomUserDetailsService
import com.kotlinspring.service.TokenService
import io.mockk.every
import io.mockk.mockk
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@TestConfiguration
@EnableWebSecurity
class TestSecurityConfiguration(
    private val customUserDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf().disable()
            .authorizeHttpRequests { authz ->
                authz.anyRequest().authenticated()
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(mockJwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        return customUserDetailsService
    }

    @Bean
    fun authenticationManager(): AuthenticationManager {
        return AuthenticationManager { authentication -> authentication }
    }

    @Bean
    fun mockJwtAuthenticationFilter(): JwtAuthenticationFilter {
        val mockUserDetailsService = mockk<CustomUserDetailsService>()
        val mockTokenService = mockk<TokenService>()

        every { mockUserDetailsService.loadUserByUsername(any()) } returns User.withUsername("testuser")
            .password("password")
            .authorities("ROLE_USER")
            .build()

        every { mockTokenService.extractUsername(any()) } returns "testuser"
        every { mockTokenService.isValid(any(), any()) } returns true

        return JwtAuthenticationFilter(mockUserDetailsService, mockTokenService)
    }
}
