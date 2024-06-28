package com.kotlinspring.service

import com.kotlinspring.controller.webSocket.WebSocketController
import com.kotlinspring.dto.budget.DailyTransactionDTO
import com.kotlinspring.dto.budget.TransactionDTO
import com.kotlinspring.dto.budget.toDTO
import com.kotlinspring.entity.TransactionEntity
import com.kotlinspring.exception.NotFoundException
import com.kotlinspring.repository.TransactionRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val webSocketController: WebSocketController
) {
    fun createTransaction(transactionDTO: TransactionDTO): TransactionDTO {
        val transactionEntity = transactionDTO.let{
            TransactionEntity(it.id, it.transactionDate, it.transactionType, it.amount, it.transactionCategory)
        }

        transactionRepository.save(transactionEntity)
        return transactionEntity.toDTO()
    }

    fun updateTransaction (id: Long, transactionDTO: TransactionDTO): TransactionDTO {
        val existingTransaction = transactionRepository.findById(id)

        return if (existingTransaction.isPresent) {
            existingTransaction.get()
                .let {
                    it.transactionDate = transactionDTO.transactionDate
                    it.transactionType = transactionDTO.transactionType
                    it.amount = transactionDTO.amount
                    it.transactionCategory = transactionDTO.transactionCategory
                    transactionRepository.save(it)
                    TransactionDTO(it.id,it.transactionDate, it.transactionType, it.amount,it.transactionCategory)
                }
        } else {
            throw NotFoundException("No transaction found for ticker symbol: $id")
        }

    }

    fun deleteTransaction(id: Long): Any{
        val existingTransaction = transactionRepository.findById(id)

        return if (existingTransaction.isPresent) {
            existingTransaction.get()
                .let {
                    transactionRepository.deleteById(id)
                }
        } else {
            throw NotFoundException("No transaction found for ticker symbol: $id")
        }
    }

    fun getAllTransactions(): List<TransactionDTO>{
        val transactions= transactionRepository.findAll()
        return transactions
            .map{
//                TransactionDTO(it.id, it.transactionDate, it.transactionType, it.amount, it.transactionCategory)
                it.toDTO()
            }
    }

    fun getTransactionsByType(transactionType: String): List<TransactionDTO>{
        val transactions = transactionRepository.findByTransactionType(transactionType)
        return transactions.map { it.toDTO() }

    }

    //Analytics

    fun getDailyTransactions(startDate: LocalDateTime, endDate: LocalDateTime): List<DailyTransactionDTO>{
        val transactions = transactionRepository.findByTransactionDateBetween(startDate, endDate)
        return transactions.groupBy { it.transactionDate.toLocalDate() }
            .map{(date, transactions) ->
                DailyTransactionDTO(
                    date = date,
                    totalIncome = transactions.filter { it.transactionType == "INCOME" }.sumOf { it.amount },
                    totalExpense = transactions.filter { it.transactionType == "EXPENSE" }.sumOf { it.amount }
                )
            }
    }
}