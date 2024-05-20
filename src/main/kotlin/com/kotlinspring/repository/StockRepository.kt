package com.kotlinspring.repository

import com.kotlinspring.entity.Stock
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface StockRepository : JpaRepository<Stock, String> {
    fun findByTickerSymbol(tickerSymbol: String): Stock?
}