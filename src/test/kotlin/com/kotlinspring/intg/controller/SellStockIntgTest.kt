package com.kotlinspring.intg.controller

import com.kotlinspring.dto.StockDTO
import com.kotlinspring.util.StockBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SellStockIntgTest : BaseStockPortfolioServiceIntgTest() {

    @Test
    fun sellStock() {

        val stockEntity = StockBuilder().price(10.0).quantity(10.0).build()
        stockRepository.save(stockEntity)
        val existingStockTickerSymbol = stockEntity.tickerSymbol
        val stockDTO = StockBuilder().tickerSymbol(existingStockTickerSymbol).price(150.0).quantity(5.0).buildDTO()
        val leftoverQuantity = stockEntity.quantity - stockDTO.quantity

        val responseStockDTO = webTestClient
            .put()
            .uri("/stocks/{tickerSymbol}/sell", existingStockTickerSymbol)
            .bodyValue(stockDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(StockDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals(stockDTO.tickerSymbol, responseStockDTO!!.tickerSymbol)
        Assertions.assertEquals(stockEntity.price, responseStockDTO.price)
        Assertions.assertEquals(leftoverQuantity, responseStockDTO.quantity)
    }

}