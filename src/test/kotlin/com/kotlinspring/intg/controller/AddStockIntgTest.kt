package com.kotlinspring.intg.controller

import com.kotlinspring.dto.StockDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AddStockIntgTest : BaseStockPortfolioServiceIntgTest() {

    @Test
    fun addStock() {
        val savedStockDTO = webTestClient
            .post()
            .uri("/api/stocks")
            .bodyValue(stockDTO)
            .headers { it.setBasicAuth("testuser", "testpassword") }
            .exchange()
            .expectStatus().isCreated
            .expectBody(StockDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue {
            savedStockDTO!!.tickerSymbol == stockDTO.tickerSymbol
        }
    }
//    @Test
//    fun addStock() {
//
//        val savedStockDTO = webTestClient
//            .post()
//            .uri("stocks")
//            .bodyValue(stockDTO)
//            .exchange()
//            .expectStatus().isCreated
//            .expectBody(StockDTO::class.java)
//            .returnResult()
//            .responseBody
//
//        Assertions.assertTrue {
//            savedStockDTO!!.tickerSymbol == stockDTO.tickerSymbol
//        }
//
//    }

    @Test
    fun addStockWithoutBuyPrice() {
        val stockDTOWithoutBuyPrice = StockDTO(
            tickerSymbol = "AAA",
            companyName = "Alpha Inc."
        )

        val savedStockDTO = webTestClient
            .post()
            .uri("/api/stocks")
            .bodyValue(stockDTOWithoutBuyPrice)
            .exchange()
            .expectStatus().isCreated
            .expectBody(StockDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue(savedStockDTO!!.tickerSymbol == stockDTOWithoutBuyPrice.tickerSymbol && savedStockDTO.price == 0.0 && savedStockDTO.quantity == 0.0)
    }

}