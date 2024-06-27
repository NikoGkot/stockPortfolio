package com.kotlinspring.intg.controller

import com.kotlinspring.dto.StockDTO
import com.kotlinspring.util.StockBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class BuyStockIntgTest :BaseStockPortfolioServiceIntgTest() {

    @Test
    fun buyStock() {

        val stockEntity = StockBuilder().price(0.0).quantity(0.0).build()
        stockRepository.save(stockEntity)
        val existingStockTickerSymbol = stockEntity.tickerSymbol
        val stockDTO = StockBuilder().tickerSymbol(existingStockTickerSymbol).price(150.0).quantity(10.0).buildDTO()

        val responseStockDTO = webTestClient
            .put()
            .uri("/api/stocks/{tickerSymbol}/buy", existingStockTickerSymbol)
            .bodyValue(stockDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(StockDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals(stockDTO.tickerSymbol, responseStockDTO!!.tickerSymbol)
        Assertions.assertEquals(stockDTO.price, responseStockDTO.price)
        Assertions.assertEquals(stockDTO.quantity, responseStockDTO.quantity)
    }

}