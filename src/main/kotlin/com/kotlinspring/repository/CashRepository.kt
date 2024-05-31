package com.kotlinspring.repository

import com.kotlinspring.entity.Cash
import org.springframework.data.jpa.repository.JpaRepository

interface CashRepository: JpaRepository<Cash, Long> {
    fun findTopByOrderByIdAsc(): Cash?
}