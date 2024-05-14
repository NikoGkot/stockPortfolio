package com.kotlinspring

import com.kotlinspring.dto.StockDTO
import com.kotlinspring.entity.Stock
import com.kotlinspring.repository.StockRepository
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient

class StockPortfolioServiceApplicationTests {

	@Autowired
	lateinit var webTestClient: WebTestClient

	@Autowired
	lateinit var stockRepository: StockRepository

	@BeforeEach
	fun setUp(){
		stockRepository.deleteAll()

	}

	@Test
	fun addStock() {
		val stockDTO= StockDTO("VUSA")

		val savedStockDTO = webTestClient
			.post()
			.uri("stocks")
			.bodyValue(stockDTO)
			.exchange()
			.expectStatus().isCreated
			.expectBody(StockDTO::class.java)
			.returnResult()
			.responseBody

		assertTrue{
			savedStockDTO!!.tickerSymbol=="VUSA"
		}

	}

}
