package com.kotlinspring.dto

import java.math.BigDecimal

data class StockDTO(
        val tickerSymbol: String,
        var companyName: String,
        var buyPrice : Double,
        var quantity : Double
)