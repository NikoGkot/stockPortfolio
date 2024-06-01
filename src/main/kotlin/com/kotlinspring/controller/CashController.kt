package com.kotlinspring.controller

import com.kotlinspring.dto.CashDTO
import com.kotlinspring.dto.DepositRequest
import com.kotlinspring.service.CashService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/cash")
class CashController (
    private val cashService: CashService
){

    @PostMapping("/initialize")
    @ResponseStatus(HttpStatus.CREATED)
    fun initialize(@RequestBody cashDTO: CashDTO): CashDTO?{
        return cashService.initialize(cashDTO)
    }

    @PutMapping("/withdraw")
    @ResponseStatus(HttpStatus.OK)
    fun withdraw(@RequestBody amount: Double){
        cashService.withdraw(amount)
    }

    @GetMapping("/balance")
    @ResponseStatus(HttpStatus.OK)
    fun getBalance(): Double{
        return cashService.getBalance()
    }
}