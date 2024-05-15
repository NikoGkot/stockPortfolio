package com.kotlinspring.intg.controller

import com.kotlinspring.repository.StockRepository
import com.kotlinspring.util.StockBuilder
import com.kotlinspring.util.stockEntityList
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
abstract class BaseStockPortfolioServiceIntgTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var stockRepository: StockRepository

    @BeforeEach
    fun setUp(){
        stockRepository.deleteAll()
        val stocks = stockEntityList()
        stockRepository.saveAll(stocks)
    }

    val stockEntity = StockBuilder().build()
    val stockDTO = StockBuilder().buildDTO()

}