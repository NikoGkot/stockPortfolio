package com.kotlinspring.intg.controller

import com.kotlinspring.TestSecurityConfig
import com.kotlinspring.repository.StockRepository
import com.kotlinspring.service.TokenService
import com.kotlinspring.util.StockBuilder
import com.kotlinspring.util.TestUtils
import com.kotlinspring.util.TestUtils.Companion.stockEntityList
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(TestSecurityConfig::class)
@AutoConfigureWebTestClient
abstract class BaseStockPortfolioServiceIntgTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var stockRepository: StockRepository

    @Autowired
    lateinit var tokenService: TokenService

    lateinit var jwtToken: String

    @Autowired
    lateinit var testUtils: TestUtils

    @BeforeEach
    fun setup() {
        // Generate or fetch a valid JWT token
        val userDetails = createTestUserDetails()
        jwtToken = tokenService.generate(
            userDetails = userDetails,
            expirationDate = Date(System.currentTimeMillis() + 3600000) // 1 hour expiration
        )

        // Initialize cash balance
        testUtils.initializeCashBalance()
    }

    private fun createTestUserDetails(): UserDetails {
        return org.springframework.security.core.userdetails.User.withUsername("testuser")
            .password("password")
            .roles("ADMIN")
            .build()
    }

    fun WebTestClient.RequestHeadersSpec<*>.withJwt(): WebTestClient.RequestHeadersSpec<*> =
        this.header("Authorization", "Bearer $jwtToken")

    @BeforeEach
    fun setUp(){

        stockRepository.deleteAll()
        val stocks = stockEntityList()
        stockRepository.saveAll(stocks)

    }

    val stockEntity = StockBuilder().build()
    val stockDTO = StockBuilder().buildDTO()

}