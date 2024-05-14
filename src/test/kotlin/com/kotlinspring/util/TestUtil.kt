package com.kotlinspring.util

import com.kotlinspring.dto.StockDTO
import com.kotlinspring.entity.Stock

fun stockEntityList() = listOf(
    Stock("VUSA", "Vanguard ETF"),
    Stock("EQQQ", "Nasdaq ETF"),
    Stock("MSFT", "Microsoft")

)

fun stockDTO(
    tickerSymbol: String = "TestTickerSymbol",
    companyName: String = "TestCompanyName"
) = StockDTO(tickerSymbol, companyName)