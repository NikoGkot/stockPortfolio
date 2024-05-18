package com.kotlinspring.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "stocks")
data class Stock(
    @Id
    val tickerSymbol: String,
    var companyName: String,
    var price: Double = 0.0,
    var quantity: Double = 0.0
//        var currentPrice : BigDecimal?,
//        var lastPriceUpdate : LocalDateTime?
) {

    val totalValue: Double
        get() = price * quantity

    override fun toString(): String {
        return "Stock(ticker symbol='$tickerSymbol')"
    }
}