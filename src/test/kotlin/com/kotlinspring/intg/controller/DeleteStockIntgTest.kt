package com.kotlinspring.intg.controller

import org.junit.jupiter.api.Test

class DeleteStockIntgTest :BaseStockPortfolioServiceIntgTest() {

    @Test
    fun deleteStock() {

        stockRepository.save(stockEntity)
        webTestClient
            .delete()
            .uri("/api/stocks/{tickerSymbol}", stockEntity.tickerSymbol)
            .exchange()
            .expectStatus().isNoContent

    }
}