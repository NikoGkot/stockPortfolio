package com.kotlinspring.util

import com.kotlinspring.dto.StockDTO
import com.kotlinspring.entity.Stock
import java.util.*
import kotlin.random.Random.Default.nextDouble
import kotlin.random.Random.Default.nextInt

class StockBuilder {
    var tickerSymbol: String = UUID.randomUUID().toString().substring(0, 4).uppercase()
    var companyName: String = "Company${nextInt(1, 1000)}"
    var price: Double = nextDouble(1.0, 100.0)
    var quantity: Double = nextDouble(1.0, 100.0)


    fun tickerSymbol(tickerSymbol: String) = apply { this.tickerSymbol = tickerSymbol }
    fun companyName(companyName: String) = apply { this.companyName = companyName }
    fun price(price: Double) = apply { this.price = price }
    fun quantity(quantity: Double) = apply { this.quantity = quantity }

    fun build(): Stock {
        return Stock(tickerSymbol, companyName, price, quantity)
    }

    fun buildDTO(): StockDTO {
        return StockDTO(tickerSymbol, companyName, price, quantity)
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