package com.kotlinspring.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name="stocks")
data class Stock (
        @Id
        val tickerSymbol : String,
        var companyName : String,
        var buyPrice : Double,
        var quantity : Double
//        var currentPrice : BigDecimal?,
//        var lastPriceUpdate : LocalDateTime?
)
{
override fun toString(): String{
        return "Stock(ticker symbol='$tickerSymbol')"
}
}