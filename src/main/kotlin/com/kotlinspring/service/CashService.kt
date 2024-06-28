package com.kotlinspring.service

import com.kotlinspring.controller.webSocket.WebSocketController
import com.kotlinspring.dto.CashDTO
import com.kotlinspring.dto.toDTO
import com.kotlinspring.entity.Cash
import com.kotlinspring.exception.AlreadyInitializedException
import com.kotlinspring.exception.CashNotInitializedException
import com.kotlinspring.repository.CashRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CashService(
    private val cashRepository: CashRepository,
    private val webSocketController: WebSocketController
) {

//    private var cashBalance: Cash? = null
//    @PostConstruct
//    fun init() {
//        if (cashRepository.count() == 0L) {
//            cashBalance = Cash(amount = 0.0)
//            cashRepository.save(cashBalance!!)
//        } else {
//            cashBalance = cashRepository.findAll().first()
//        }
//    }

    fun initialize(cashDTO: CashDTO): CashDTO? {
        val found = cashRepository.findTopByOrderByIdAsc()
        return if (found == null) {
            val cash = cashDTO.let {
                Cash(it.id, it.amount)
            }
            cashRepository.save(cash)
            return cash.toDTO()
        } else {
            throw AlreadyInitializedException("Cash register has already been initialized. Try deposit instead")
        }
    }

    @Transactional
    fun deposit(amount: Double): CashDTO {
        val cash = cashRepository.findTopByOrderByIdAsc() ?: throw CashNotInitializedException("Cash record not found")
        cash.amount += amount
        cashRepository.save(cash)
        webSocketController.sendRefreshMessage()
        return cash.toDTO()
    }

    @Transactional
    fun withdraw(amount: Double): CashDTO {
        val cash = cashRepository.findTopByOrderByIdAsc() ?: throw CashNotInitializedException("Cash record not found")
        if (cash.amount < amount) {
            throw IllegalArgumentException("Insufficient funds")
        }
        cash.amount -= amount
        cashRepository.save(cash)

        webSocketController.sendRefreshMessage()


        return cash.toDTO()
    }

    @Transactional
    fun getBalance(): Double {
        return cashRepository.findTopByOrderByIdAsc()?.amount ?: 0.0
    }
}