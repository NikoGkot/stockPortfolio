package com.kotlinspring.service

import com.kotlinspring.dto.StockDTO
import com.kotlinspring.entity.Stock
import com.kotlinspring.exception.StockNotFoundException
import com.kotlinspring.repository.StockRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class StockService(
        val stockRepository: StockRepository) {

    companion object : KLogging()

    fun addStock(stockDTO: StockDTO): StockDTO {

        val stockEntity = stockDTO.let {
            Stock(it.tickerSymbol, it.companyName, it.buyPrice, it.quantity)
        }

        stockRepository.save(stockEntity)

        logger.info { "Saved stock is: $stockEntity" }
        return stockEntity.let {
            StockDTO(it.tickerSymbol, it.companyName,it.buyPrice, it.quantity)
        }
    }

    fun retrieveAllStocks(stockTickerSymbol: String?): List<StockDTO>{

        val stocks = stockTickerSymbol?.let{
            stockRepository.findByTickerSymbol(stockTickerSymbol)
        }?: stockRepository.findAll()
//        val stocks = stockRepository.findAll()
        return stocks
            .map{
                StockDTO(it.tickerSymbol, it.companyName,it.buyPrice, it.quantity)
            }
    }

    fun getStockByTickerSymbol(tickerSymbol: String): List<StockDTO> {
        val stock = stockRepository.findByTickerSymbol(tickerSymbol)
            ?: throw Exception("Stock not found") // Handle not found case appropriately
        return stock.map {StockDTO(it.tickerSymbol, it.companyName, it.buyPrice, it.quantity)}
    }

    fun updateStock(stockTickerSymbol: String, stockDTO: StockDTO) :StockDTO {
        val existingStock = stockRepository.findById(stockTickerSymbol)

        return if(existingStock.isPresent){
            existingStock.get()
                .let {
                    it.companyName = stockDTO.companyName
                    it.buyPrice = stockDTO.buyPrice
                    it.quantity = stockDTO.quantity
                    stockRepository.save(it)
                    StockDTO(it.tickerSymbol, it.companyName,it.buyPrice, it.quantity)
                }
        }else{
            throw StockNotFoundException("No stock found for ticker symbol: $stockTickerSymbol")
        }
    }

    fun deleteStock(stockTickerSymbol: String): Any {
        val existingStock = stockRepository.findById(stockTickerSymbol)

        return if(existingStock.isPresent){
            existingStock.get().let {
                stockRepository.deleteById(stockTickerSymbol)
            }
        }else{
            throw StockNotFoundException("No stock found for ticker symbol: $stockTickerSymbol")
        }
    }
}

