package com.kotlinspring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StockPortfolioServiceApplication

fun main(args: Array<String>) {
	runApplication<StockPortfolioServiceApplication>(*args)
}
