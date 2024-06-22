package com.kotlinspring.controller

import com.kotlinspring.dto.TransactionDTO
import com.kotlinspring.service.TransactionService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/transaction")
@Validated
class TransactionController(val transactionService: TransactionService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createTransaction(@Valid @RequestBody transactionDTO: TransactionDTO): TransactionDTO{
        return transactionService.createTransaction(transactionDTO)
    }

    @PutMapping("id")
    fun updateTransaction(
        @PathVariable id:Long,
        @RequestBody @Valid transactionDTO: TransactionDTO
    ): TransactionDTO{
        return transactionService.updateTransaction(id, transactionDTO)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAllTransactions():List<TransactionDTO>{
        return transactionService.getAllTransactions()
    }

    @GetMapping("type/{transactionType}")
    @ResponseStatus(HttpStatus.OK)
    fun getAllTransactionsOfType(@PathVariable transactionType: String):List<TransactionDTO>{
        return transactionService.getTransactionsByType(transactionType)
    }
}