package com.kotlinspring.intg.controller

import com.kotlinspring.dto.StockDTO
import com.kotlinspring.repository.StockRepository
import com.kotlinspring.util.PostgreSQLContainerInitializer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient

class StockControllerIntgTest : PostgreSQLContainerInitializer(){

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var stockRepository: StockRepository

    @BeforeEach
    fun setUp(){
        stockRepository.deleteAll()
    }

    @Test
    fun addStock(){
        val stockDTO = StockDTO( "VUSA")

        val savedStockDTO = webTestClient
                .post()
                .uri("/stocks")
                .bodyValue(stockDTO)
                .exchange()
                .expectStatus().isCreated
                .expectBody(StockDTO::class.java)
                .returnResult()
                .responseBody

        Assertions.assertTrue {
            savedStockDTO!!.tickerSymbol == "VUSA"
        }
    }

}