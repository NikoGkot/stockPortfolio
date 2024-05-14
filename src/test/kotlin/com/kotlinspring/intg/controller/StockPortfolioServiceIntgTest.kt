package com.kotlinspring.intg.controller

import com.kotlinspring.dto.StockDTO
import com.kotlinspring.entity.Stock
import com.kotlinspring.repository.StockRepository
import com.kotlinspring.util.stockEntityList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.util.UriComponentsBuilder

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient

class StockPortfolioServiceIntgTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var stockRepository: StockRepository

    @BeforeEach
    fun setUp() {
        stockRepository.deleteAll()

        val stocks = stockEntityList()
        stockRepository.saveAll(stocks)

    }

    @Test
    fun addStock() {
        val stockDTO = StockDTO("TestStockTicker", "TestCompanyName")

        val savedStockDTO = webTestClient
            .post()
            .uri("stocks")
            .bodyValue(stockDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(StockDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue {
            savedStockDTO!!.tickerSymbol == "TestStockTicker"
        }

    }

    @Test
    fun retrieveAllStocks() {
        val stockDTOs = webTestClient
            .get()
            .uri("stocks")
            .exchange()
            .expectBodyList(StockDTO::class.java)
            .returnResult()
            .responseBody

        println("stockDTOs : $stockDTOs")
        assertEquals(3, stockDTOs!!.size)
    }

    @Test
    fun retrieveAllStocks_ByTickerSymbol() {
        val uri = UriComponentsBuilder.fromUriString("stocks")
            .queryParam("stock_tickerSymbol", "EQQQ")
            .toUriString()

        val stockDTOs = webTestClient
            .get()
            .uri(uri)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(StockDTO::class.java)
            .returnResult()
            .responseBody

        println("stockDTOs : $stockDTOs")
        assertEquals(1, stockDTOs!!.size)
    }

    @Test
    fun updateStock() {
        val stockEntity = Stock(
            "TestTickerSymbol", "TestCompanyName"
        )
        stockRepository.save(stockEntity)
        val updatedStockDTO = StockDTO(
            "TestTickerSymbol", "TestCompanyName1"
        )

        val updatedStock = webTestClient
            .put()
            .uri("stocks/{stock_tickerSymbol}", stockEntity.tickerSymbol)
            .bodyValue(updatedStockDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(StockDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals("TestCompanyName1", updatedStock?.companyName)
    }

}