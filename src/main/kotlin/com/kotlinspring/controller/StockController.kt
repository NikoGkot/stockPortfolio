package com.kotlinspring.controller

import com.kotlinspring.dto.StockDTO
import com.kotlinspring.service.StockService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.security.auth.kerberos.KerberosTicket


@RestController
@RequestMapping("stocks")
@Validated
class StockController(val stockService: StockService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addStock(@RequestBody @Valid stockDTO: StockDTO): StockDTO {
        return stockService.addStock(stockDTO)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun retrieveAllStocks(
        @RequestParam(
            "stock_tickerSymbol",
            required = false
        ) stockTickerSymbol: String?
    ): List<StockDTO> = stockService.retrieveAllStocks(stockTickerSymbol)

    @GetMapping("/{tickerSymbol}")
    fun getStockByTickerSymbol(@PathVariable tickerSymbol: String):List<StockDTO>{
        return stockService.getStockByTickerSymbol(tickerSymbol)
    }

    @PutMapping("/{stock_tickerSymbol}")
    fun updateStock(
        @RequestBody stockDTO: StockDTO,
        @PathVariable("stock_tickerSymbol") stockTickerSymbol: String
    ): StockDTO = stockService.updateStock(stockTickerSymbol, stockDTO)

    @DeleteMapping("/{stock_tickerSymbol}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteStock(
        @PathVariable("stock_tickerSymbol") stockTickerSymbol: String
    ) = stockService.deleteStock(stockTickerSymbol)


}