// src/test/kotlin/com/kotlinspring/controller/StockControllerTest.kt
package com.kotlinspring.unit.controller

import com.kotlinspring.TestSecurityConfig
import com.kotlinspring.controller.StockController
import com.kotlinspring.service.StockService
//import com.kotlinspring.util.TestConfig
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(StockController::class)
@Import(TestSecurityConfig::class)
class StockControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var stockService: StockService

    @Autowired
    private lateinit var jwtToken: String

    @Test
    fun `test get stocks`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/stocks")
            .header("Authorization", "Bearer $jwtToken")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
    }

}
