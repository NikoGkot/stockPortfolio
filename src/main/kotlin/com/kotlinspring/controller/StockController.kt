package com.kotlinspring.controller

import com.kotlinspring.dto.StockDTO
import com.kotlinspring.service.StockService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("stocks")
@Validated
class StockController(val stockService: StockService) {

    //Basic Mappings

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addStock(@RequestBody @Valid stockDTO: StockDTO): StockDTO {
        return stockService.addStock(stockDTO)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun retrieveAllStocks(): List<StockDTO> = stockService.retrieveAllStocks()

    @GetMapping("/{tickerSymbol}")
    fun getStockByTickerSymbol(@PathVariable tickerSymbol: String): StockDTO {
        return stockService.getStockByTickerSymbol(tickerSymbol)
    }

    @PutMapping("/{stock_tickerSymbol}")
    fun updateStock(
        @RequestBody stockDTO: StockDTO,
        @PathVariable("stock_tickerSymbol") stockTickerSymbol: String
    ): StockDTO = stockService.updateStock(stockTickerSymbol, stockDTO)

    @DeleteMapping("/{tickerSymbol}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteStock(
        @PathVariable tickerSymbol: String
    ) = stockService.deleteStock(tickerSymbol)

    //Secondary Mappings

    @PutMapping("/{tickerSymbol}/buy")
    @ResponseStatus(HttpStatus.OK)
    fun buyStock(
        @PathVariable tickerSymbol: String,
        @RequestBody @Valid stockDTO: StockDTO
    ): StockDTO {
        return stockService.buyStock(tickerSymbol, stockDTO)
    }

    @PutMapping("/{tickerSymbol}/sell")
    @ResponseStatus(HttpStatus.OK)
    fun sellStock(
        @PathVariable tickerSymbol: String,
        @RequestBody @Valid stockDTO: StockDTO
    ): StockDTO {
        return stockService.sellStock(tickerSymbol, stockDTO)
    }


}