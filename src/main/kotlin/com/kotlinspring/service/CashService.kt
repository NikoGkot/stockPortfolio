package com.kotlinspring.service

import com.kotlinspring.dto.CashDTO
import com.kotlinspring.dto.DepositRequest
import com.kotlinspring.dto.toDTO
import com.kotlinspring.entity.Cash
import com.kotlinspring.repository.CashRepository
import jakarta.annotation.PostConstruct
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CashService(
    private val cashRepository: CashRepository
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
        return if (found==null){
            val cash = cashDTO.let{
                Cash(it.id, it.amount)
            }
            cashRepository.save(cash)
            return cash.toDTO()
        } else null
    }

    @Transactional
    fun deposit(amount: Double) {
        val cash = cashRepository.findTopByOrderByIdAsc() ?: Cash(id = 0, amount = 0.0)
//        cash.amount += amount
        cashRepository.save(cash)
    }

    @Transactional
    fun withdraw(amount: Double) {
        val cash = cashRepository.findTopByOrderByIdAsc() ?: throw IllegalArgumentException("Cash record not found")
        if (cash.amount < amount) {
            throw IllegalArgumentException("Insufficient funds")
        }
        cash.amount -= amount
        cashRepository.save(cash)
    }

    @Transactional
    fun getBalance(): Double {
        return cashRepository.findTopByOrderByIdAsc()?.amount ?: 0.0
    }
}