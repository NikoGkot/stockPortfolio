package com.kotlinspring.dto

import com.kotlinspring.entity.Cash

data class CashDTO(
    val id: Long,
    val amount: Double
)

fun Cash.toDTO(): CashDTO{
    return CashDTO(
        id = this.id,
        amount = this.amount
    )
}
