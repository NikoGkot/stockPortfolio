package com.kotlinspring.util

import com.kotlinspring.dto.StockDTO
import com.kotlinspring.entity.Stock
import java.math.BigDecimal
import java.util.*
import kotlin.random.Random.Default.nextDouble
import kotlin.random.Random.Default.nextInt

class StockBuilder {
    var tickerSymbol: String = UUID.randomUUID().toString().substring(0, 4).uppercase()
    var companyName: String = "Company${nextInt(1, 1000)}"
    var buyPrice: Double = nextDouble(1.0, 100.0)
    var quantity: Double = nextDouble(1.0, 100.0)


    fun tickerSymbol(tickerSymbol: String) = apply { this.tickerSymbol = tickerSymbol }
    fun companyName(companyName: String) = apply { this.companyName = companyName }
    fun buyPrice(buyPrice: Double) = apply { this.buyPrice = buyPrice }
    fun quantity(quantity: Double) = apply { this.quantity = quantity }

    fun build(): Stock {
        return Stock(tickerSymbol, companyName, buyPrice, quantity)
    }

    fun buildDTO(): StockDTO {
        return StockDTO(tickerSymbol, companyName, buyPrice, quantity)
    }
}
//
//class StockDTOBuilder {
//    private var tickerSymbol: String = UUID.randomUUID().toString().substring(0, 4).uppercase()
//    private var companyName: String = "Company${nextInt(1, 1000)}"
//    private var buyPrice: Double = nextDouble(1.0, 100.0)
//    private var quantity: Double = nextDouble(1.0, 100.0)
//    fun build() = Stock(tickerSymbol, companyName, buyPrice, quantity)
//}