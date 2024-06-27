package com.kotlinspring.intg.controller

import com.kotlinspring.dto.StockDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RetrieveStockIntgTest :BaseStockPortfolioServiceIntgTest() {

    @Test
    fun retrieveAllStocks() {
        val stockDTOs = webTestClient
            .get()
            .uri("/api/stocks")
            .exchange()
            .expectBodyList(StockDTO::class.java)
            .returnResult()
            .responseBody

        println("stockDTOs : $stockDTOs")
        Assertions.assertEquals(3, stockDTOs!!.size)
    }

    @Test
    fun retrieveAllStocks_ByTickerSymbol() {

//        val uri = UriComponentsBuilder.fromUriString("stocks")
//            .queryParam("tickerSymbol", "EQQQ")
//            .toUriString()

        val stockDTOs = webTestClient
            .get()
            .uri("/api/stocks/{stock_tickerSymbol}", "EQQQ")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(StockDTO::class.java)
            .returnResult()
            .responseBody

        println("stockDTOs : $stockDTOs")
        Assertions.assertEquals(1, stockDTOs!!.size)
    }

}