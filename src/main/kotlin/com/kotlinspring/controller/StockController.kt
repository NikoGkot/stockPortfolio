package com.kotlinspring.controller

import com.kotlinspring.dto.StockDTO
import com.kotlinspring.service.StockService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("stocks")
@Validated
class StockController (val stockService: StockService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addStock(@RequestBody @Valid stockDTO: StockDTO): StockDTO{
        return stockService.addStock(stockDTO)
    }
}