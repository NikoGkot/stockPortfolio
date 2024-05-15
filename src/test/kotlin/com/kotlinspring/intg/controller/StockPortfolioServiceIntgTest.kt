package com.kotlinspring.intg.controller

import com.kotlinspring.dto.StockDTO
import com.kotlinspring.entity.Stock
import com.kotlinspring.repository.StockRepository
import com.kotlinspring.util.StockBuilder
import com.kotlinspring.util.stockEntityList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
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

    //    Builder
    val stockEntity = StockBuilder().build()
    val stockDTO = StockBuilder().buildDTO()

    @Test
    fun addStock() {

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
            savedStockDTO!!.tickerSymbol == stockDTO.tickerSymbol
        }

    }

    @Test
    fun addStockWithoutBuyPrice() {
        val stockDTOWithoutBuyPrice = StockDTO(
            tickerSymbol = "AAA",
            companyName = "Alpha Inc."
        )

        val savedStockDTO = webTestClient
            .post()
            .uri("stocks")
            .bodyValue(stockDTOWithoutBuyPrice)
            .exchange()
            .expectStatus().isCreated
            .expectBody(StockDTO::class.java)
            .returnResult()
            .responseBody

        assertTrue(savedStockDTO!!.tickerSymbol == stockDTOWithoutBuyPrice.tickerSymbol && savedStockDTO.buyPrice == 0.0 && savedStockDTO.quantity == 0.0)
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


        stockRepository.save(stockEntity)

        val updatedStockDTO = StockBuilder().tickerSymbol("TestTickerSymbol").companyName("TestCompanyName1").buildDTO()


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

    @Test
    fun deleteStock() {

        stockRepository.save(stockEntity)
        webTestClient
            .delete()
            .uri("/stocks/{stock_tickerSymbol}", stockEntity.tickerSymbol)
            .exchange()
            .expectStatus().isNoContent

    }

}