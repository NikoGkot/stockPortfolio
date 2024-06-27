package com.kotlinspring.util

import com.kotlinspring.dto.CashDTO
import com.kotlinspring.dto.StockDTO
import com.kotlinspring.service.CashService
import org.springframework.stereotype.Component

@Component
class TestUtils(private val cashService: CashService) {

    fun initializeCashBalance(amount: Double = 10000.0) {
        try {
            cashService.initialize(CashDTO(id=1, amount = amount))
        } catch (e: Exception) {
            // If cash is already initialized, we can ignore this exception
            // and proceed with depositing the amount
            cashService.deposit(amount)
        }
    }

    companion object {
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
    }
}