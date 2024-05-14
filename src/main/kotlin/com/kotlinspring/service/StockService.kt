package com.kotlinspring.service

import com.kotlinspring.dto.StockDTO
import com.kotlinspring.entity.Stock
import com.kotlinspring.repository.StockRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class StockService(
        val stockRepository: StockRepository) {

    companion object : KLogging()

    fun addStock(stockDTO: StockDTO): StockDTO {

        val stockEntity = stockDTO.let {
            Stock(it.tickerSymbol)
        }

        stockRepository.save(stockEntity)

        logger.info { "Saved stock is: $stockEntity" }
        return stockEntity.let {
            StockDTO(it.tickerSymbol)
        }
    }

    fun retrieveAllStocks(stockTickerSymbol: String?): List<StockDTO>{

        val stocks = stockTickerSymbol?.let{
            stockRepository.findByTickerSymbol(stockTickerSymbol)
        }?: stockRepository.findAll()
//        val stocks = stockRepository.findAll()
        return stocks
            .map{
                StockDTO(it.tickerSymbol)
            }
    }
}

