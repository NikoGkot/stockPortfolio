package com.kotlinspring.dto

import com.kotlinspring.entity.Stock

data class StockDTO(
        val tickerSymbol: String,
        var companyName: String = "",
        var price : Double = 0.0, //Default Value
        var quantity : Double = 0.0,
        val totalValue : Double = quantity * price
)

fun Stock.toDTO(): StockDTO{
        return StockDTO(
                tickerSymbol = this.tickerSymbol,
                companyName = this.companyName,
                price = this.price,
                quantity = this.quantity,
                totalValue = this.totalValue
        )
}