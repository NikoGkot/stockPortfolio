package com.kotlinspring.repository

import com.kotlinspring.entity.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface TransactionRepository: JpaRepository<TransactionEntity, Long> {
    fun findByTransactionType(transactionType: String): List<TransactionEntity>

    //Analytics
    //Custom query is not necessary in this case since findByTransactionDateBetween is a custom syntax acceptable by JPARepository framework
    @Query(""" 
        SELECT t FROM TransactionEntity t
        WHERE t.transactionDate BETWEEN :startDate AND :endDate
        ORDER BY t.transactionDate
    """)
    fun findByTransactionDateBetween(
        @Param("startDate")startDate: LocalDateTime,
        @Param("endDate")endDate: LocalDateTime
    ): List<TransactionEntity>

}