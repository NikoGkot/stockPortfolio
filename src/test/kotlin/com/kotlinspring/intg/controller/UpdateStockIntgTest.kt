package com.kotlinspring.intg.controller

import com.kotlinspring.dto.StockDTO
import com.kotlinspring.util.StockBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UpdateStockIntgTest : BaseStockPortfolioServiceIntgTest() {

    @Test
    fun updateStock() {
        stockRepository.save(stockEntity)

        val updatedStockDTO = StockBuilder().companyName("TestCompanyName1").buildDTO()


        val updatedStock = webTestClient
            .put()
            .uri("/api/stocks/{stock_tickerSymbol}", stockEntity.tickerSymbol)
            .bodyValue(updatedStockDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(StockDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals("TestCompanyName1", updatedStock?.companyName)
    }

}