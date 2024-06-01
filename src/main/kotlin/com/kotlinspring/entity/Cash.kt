package com.kotlinspring.entity

import jakarta.persistence.*

@Entity
@Table(name="cash")
data class Cash(
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "cash_seq")
    @SequenceGenerator(name = "cash_seq", sequenceName = "cash_sequence", allocationSize = 1)
    val id: Long,
    var amount: Double
)
