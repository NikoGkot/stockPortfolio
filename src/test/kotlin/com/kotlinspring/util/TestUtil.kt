package com.kotlinspring.util

import com.kotlinspring.dto.StockDTO
import com.kotlinspring.entity.Stock

fun stockEntityList()= listOf(
    Stock("VUSA"),
    Stock("EQQQ"),
    Stock("MSFT")

)

fun stockDTO(
    tickerSymbol: String = "Default"
)= StockDTO(tickerSymbol)