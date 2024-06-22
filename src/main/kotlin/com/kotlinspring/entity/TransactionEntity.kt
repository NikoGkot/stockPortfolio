package com.kotlinspring.entity

import jakarta.persistence.*

import java.time.LocalDateTime

@Entity
@Table(name="transactions")
data class TransactionEntity (
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
    @SequenceGenerator(name = "transaction_seq", sequenceName = "transaction_sequence", allocationSize = 1)
    val id: Long,

    @Column(nullable = false)
    var transactionDate: LocalDateTime,

    @Column(nullable = false)
    var transactionType: String,

    @Column(nullable = false)
    var amount: Double,

    @Column(nullable = true)
    var transactionCategory: String?
)