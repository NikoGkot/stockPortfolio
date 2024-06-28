package com.kotlinspring.repository

import com.kotlinspring.entity.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface TransactionRepository: JpaRepository<TransactionEntity, Long> {
    fun findByTransactionType(transactionType: String): List<TransactionEntity>

    //Analytics
    fun findByTransactionDateBetween(startDate: LocalDateTime, endDate: LocalDateTime): List<TransactionEntity>

}