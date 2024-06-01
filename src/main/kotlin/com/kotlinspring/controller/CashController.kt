package com.kotlinspring.controller

import com.kotlinspring.dto.CashDTO
import com.kotlinspring.dto.DepositRequest
import com.kotlinspring.dto.WithdrawRequest
import com.kotlinspring.service.CashService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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


    @PutMapping("/deposit")
    fun deposit(@RequestBody depositRequest: DepositRequest): ResponseEntity<CashDTO> {
        val updatedCash = cashService.deposit(depositRequest.amount)
        return ResponseEntity.ok(updatedCash)
    }

    @PutMapping("/withdraw")
    fun withdraw(@RequestBody withDrawRequest: WithdrawRequest): ResponseEntity<CashDTO>{
        val updatedCash = cashService.withdraw(withDrawRequest.amount)
        return ResponseEntity.ok(updatedCash)
    }

    @GetMapping("/balance")
    @ResponseStatus(HttpStatus.OK)
    fun getBalance(): Double{
        return cashService.getBalance()
    }
}