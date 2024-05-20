package com.kotlinspring.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import kotlin.jvm.Throws

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers(HttpMethod.GET).permitAll()
                    .anyRequest().authenticated()
            }
            .formLogin { form ->
                form
                    .permitAll()
            }
            .logout { logout ->
                logout
                    .permitAll()
            }


        return http.build()

    }
}