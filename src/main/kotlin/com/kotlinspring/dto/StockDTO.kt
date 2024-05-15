package com.kotlinspring.dto

import java.math.BigDecimal

data class StockDTO(
        val tickerSymbol: String,
        var companyName: String,
        var buyPrice : Double = 0.0, //Default Value
        var quantity : Double = 0.0
)