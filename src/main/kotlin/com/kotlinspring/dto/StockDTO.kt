package com.kotlinspring.dto

data class StockDTO(
        val tickerSymbol: String,
        var companyName: String,
        var price : Double = 0.0, //Default Value
        var quantity : Double = 0.0
)