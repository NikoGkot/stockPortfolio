package com.kotlinspring.intg.controller

import com.kotlinspring.dto.StockDTO
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

        assertTrue(savedStockDTO!!.tickerSymbol == stockDTOWithoutBuyPrice.tickerSymbol && savedStockDTO.price == 0.0 && savedStockDTO.quantity == 0.0)
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

//        val uri = UriComponentsBuilder.fromUriString("stocks")
//            .queryParam("tickerSymbol", "EQQQ")
//            .toUriString()

        val stockDTOs = webTestClient
            .get()
            .uri("stocks/{stock_tickerSymbol}", "EQQQ")
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

        val updatedStockDTO = StockBuilder().companyName("TestCompanyName1").buildDTO()


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
            .uri("/stocks/{tickerSymbol}", stockEntity.tickerSymbol)
            .exchange()
            .expectStatus().isNoContent

    }

    @Test
    fun buyStock() {

        val stockEntity = StockBuilder().price(0.0).quantity(0.0).build()
        stockRepository.save(stockEntity)
        val existingStockTickerSymbol = stockEntity.tickerSymbol
        val stockDTO = StockBuilder().tickerSymbol(existingStockTickerSymbol).price(150.0).quantity(10.0).buildDTO()

        val responseStockDTO = webTestClient
            .put()
            .uri("/stocks/{tickerSymbol}/buy", existingStockTickerSymbol)
            .bodyValue(stockDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(StockDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals(stockDTO.tickerSymbol, responseStockDTO!!.tickerSymbol)
        assertEquals(stockDTO.price, responseStockDTO.price)
        assertEquals(stockDTO.quantity, responseStockDTO.quantity)
    }

    @Test
    fun sellStock() {

        val stockEntity = StockBuilder().price(10.0).quantity(10.0).build()
        stockRepository.save(stockEntity)
        val existingStockTickerSymbol = stockEntity.tickerSymbol
        val stockDTO = StockBuilder().tickerSymbol(existingStockTickerSymbol).price(150.0).quantity(5.0).buildDTO()
        val leftoverQuantity = stockEntity.quantity-stockDTO.quantity

        val responseStockDTO = webTestClient
            .put()
            .uri("/stocks/{tickerSymbol}/sell", existingStockTickerSymbol)
            .bodyValue(stockDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(StockDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals(stockDTO.tickerSymbol, responseStockDTO!!.tickerSymbol)
        assertEquals(stockEntity.price, responseStockDTO.price)
        assertEquals(leftoverQuantity, responseStockDTO.quantity)
    }

}