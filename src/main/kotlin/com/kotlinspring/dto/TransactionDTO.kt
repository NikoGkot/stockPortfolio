package com.kotlinspring.dto

import com.kotlinspring.entity.TransactionEntity
import jakarta.validation.constraints.Min
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime


data class TransactionDTO (
    val id:Long,

    @field:NotNull("Transaction date cannot be null")
    val transactionDate: LocalDateTime,

    @field:NotNull("Transaction type cannot be null")
    val transactionType: String,

    @field:Min(value = 0, message = "Amount must be greater than or equal to: 0")
    val amount: Double,
    val transactionCategory: String?
)

fun TransactionEntity.toDTO(): TransactionDTO{
    return TransactionDTO(
        id = this.id,
        transactionDate = this.transactionDate,
        transactionType = this.transactionType,
        amount= this.amount,
        transactionCategory = this.transactionCategory

    )
}