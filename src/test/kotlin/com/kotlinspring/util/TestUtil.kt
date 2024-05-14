package com.kotlinspring.util

import com.kotlinspring.dto.StockDTO
import com.kotlinspring.entity.Stock

fun main() {
    val stockEntityList = stockEntityList()
    stockEntityList.forEach { println(it) }
}

//fun stockEntityList(): List<Stock> {
//    val stockBuilder = StockBuilder()
//    return listOf(
//        stockBuilder.build(),
//        stockBuilder.build(),
//        stockBuilder.build(),
//    )
//}

fun stockEntityList() = listOf(
    StockBuilder().tickerSymbol("VUSA").companyName("Vanguard ETF").build(),
    StockBuilder().tickerSymbol("EQQQ").companyName("Nasdaq ETF").build(),
    StockBuilder().tickerSymbol("MSFT").companyName("Microsoft").build()
)
fun stockDTOList(): List<StockDTO> {
    val stockBuilder = StockBuilder()
    return listOf(
        stockBuilder.buildDTO(),
        stockBuilder.buildDTO(),
        stockBuilder.buildDTO()
    )
}

fun stockDTO(
    tickerSymbol: String = "TestTickerSymbol",
    companyName: String = "TestCompanyName"
): StockDTO {
    val stockBuilder = StockBuilder()
    stockBuilder.tickerSymbol = tickerSymbol
    stockBuilder.companyName = companyName
    return stockBuilder.buildDTO()
}