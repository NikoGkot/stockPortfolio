package com.kotlinspring.entity

import jakarta.persistence.*

@Entity
@Table(name="cash")
data class Cash(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long =0,

    @Column(nullable = false)
    var amount: Double
)
