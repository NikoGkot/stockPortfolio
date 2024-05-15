package com.kotlinspring.repository

import com.kotlinspring.entity.Stock
import org.springframework.data.repository.CrudRepository

interface StockRepository : CrudRepository <Stock, String> {
    fun findByTickerSymbol(tickerSymbol: String): Stock?
}