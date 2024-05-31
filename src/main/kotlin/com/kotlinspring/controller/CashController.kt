package com.kotlinspring.controller

import com.kotlinspring.dto.DepositRequest
import com.kotlinspring.service.CashService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/cash")
class CashController (
    private val cashService: CashService
){

    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.OK)
    fun deposit(@RequestParam depositRequest: DepositRequest){
        cashService.deposit(depositRequest.amount)
    }

    @PutMapping("/withdraw")
    @ResponseStatus(HttpStatus.OK)
    fun withdraw(@RequestParam amount: Double){
        cashService.withdraw(amount)
    }

    @GetMapping("/balance")
    @ResponseStatus(HttpStatus.OK)
    fun getBalance(): Double{
        return cashService.getBalance()
    }
}