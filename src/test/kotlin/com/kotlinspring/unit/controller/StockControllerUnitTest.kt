package com.kotlinspring.controller

import com.kotlinspring.dto.StockDTO
import com.kotlinspring.service.StockService
import com.kotlinspring.util.TestUtils
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.*

class StockControllerUnitTest {

    private lateinit var stockController: StockController
    private lateinit var stockService: StockService

    @BeforeEach
    fun setUp() {
        stockService = mockk()
        stockController = StockController(stockService)
        println("Test setup complete. StockController and mocked StockService are ready.")
    }

    @Test
    fun `retrieveAllStocks should return list of all stocks`() {
        // Given
        val stocks = TestUtils.stockDTOList()
        every { stockService.retrieveAllStocks() } returns stocks
//        println("Mock setup: stockService.retrieveAllStocks() will return ${stocks.size} stocks.")

        // When
//        println("Calling stockController.retrieveAllStocks()")
        val result = stockController.retrieveAllStocks()

        // Then
//        println("Verifying results...")
        assertEquals(stocks, result)
//        println("Assertion passed: returned list matches expected stocks.")
        verify(exactly = 1) { stockService.retrieveAllStocks() }
//        println("Verified: stockService.retrieveAllStocks() was called exactly once.")

//        println("Test result: ${result.size} stocks retrieved:")
        result.forEachIndexed { index, stock ->
            println("  ${index + 1}. ${stock.tickerSymbol}: ${stock.companyName}")
        }
    }

    @Test
    fun `getStockByTickerSymbol should return stock when it exists`() {
        // Given
        val tickerSymbol = "AAPL"
        val stock = TestUtils.stockDTO(tickerSymbol = tickerSymbol)
        every { stockService.getStockByTickerSymbol(tickerSymbol) } returns stock
//        println("Mock setup: stockService.getStockByTickerSymbol('$tickerSymbol') will return $stock")

        // When
//        println("Calling stockController.getStockByTickerSymbol('$tickerSymbol')")
        val result = stockController.getStockByTickerSymbol(tickerSymbol)

        // Then
//        println("Verifying results...")
        assertEquals(stock, result)
//        println("Assertion passed: returned stock matches expected stock.")
        verify(exactly = 1) { stockService.getStockByTickerSymbol(tickerSymbol) }
//        println("Verified: stockService.getStockByTickerSymbol('$tickerSymbol') was called exactly once.")

//        println("Test result: Retrieved stock details:")
//        println("  Ticker: ${result.tickerSymbol}")
//        println("  Company: ${result.companyName}")
//        println("  Price: ${result.price}")
//        println("  Quantity: ${result.quantity}")
    }

    @Test
    fun `addStock should create and return a new stock`() {
        // Given
        val inputStockDTO = TestUtils.stockDTO()
        every { stockService.addStock(inputStockDTO) } returns inputStockDTO

        // When
        val result = stockController.addStock(inputStockDTO)

        // Then
        assertEquals(inputStockDTO, result)
        verify(exactly = 1) { stockService.addStock(inputStockDTO) }
    }


}