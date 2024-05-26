package com.kotlinspring.service

import com.kotlinspring.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

typealias ApplicationUser = com.kotlinspring.entity.UserEntity

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByUsername(username)
            ?.mapToUserDetails()
            ?: throw UsernameNotFoundException("User not found")
    }

    private fun ApplicationUser.mapToUserDetails(): UserDetails {
        val authorities = this.roles.map { SimpleGrantedAuthority(it?.name) }.toSet()
        return User.builder()
            .username(this.username)
            .password(this.password)
            .authorities(authorities)
            .build()
    }
}