package com.kotlinspring.repository

import com.kotlinspring.entity.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionRepository: JpaRepository<TransactionEntity, Long> {
    fun findByTransactionType(transactionType: String): List<TransactionEntity>
}