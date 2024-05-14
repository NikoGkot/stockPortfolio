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
class StockController (val stockService: StockService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addStock(@RequestBody @Valid stockDTO: StockDTO): StockDTO{
        return stockService.addStock(stockDTO)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun retrieveAllCourses(@RequestParam("stock_tickerSymbol", required = false) stockTickerSymbol: String?): List<StockDTO>
            = stockService.retrieveAllStocks(stockTickerSymbol)
}