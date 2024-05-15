package com.kotlinspring.service

import com.kotlinspring.dto.StockDTO
import com.kotlinspring.dto.toDTO
import com.kotlinspring.entity.Stock
import com.kotlinspring.exception.InsufficientStockQuantityException
import com.kotlinspring.exception.StockNotFoundException
import com.kotlinspring.repository.StockRepository
import jakarta.transaction.Transactional
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class StockService(
    val stockRepository: StockRepository
) {

    companion object : KLogging()

    fun addStock(stockDTO: StockDTO): StockDTO {

        val stockEntity = stockDTO.let {
            Stock(it.tickerSymbol, it.companyName, it.price, it.quantity)
        }

        stockRepository.save(stockEntity)

        logger.info { "Saved stock is: $stockEntity" }
        return stockEntity.toDTO()
    }

    fun retrieveAllStocks(): List<StockDTO> {

//        val stocks = stockTickerSymbol?.let {
//            stockRepository.findByTickerSymbol(stockTickerSymbol)
//        } ?: stockRepository.findAll()
        val stocks = stockRepository.findAll()
        return stocks
            .map {
                StockDTO(it.tickerSymbol, it.companyName, it.price, it.quantity, it.totalValue)
            }
    }

    fun getStockByTickerSymbol(tickerSymbol: String): StockDTO {
        val stock = stockRepository.findByTickerSymbol(tickerSymbol)
            ?: throw StockNotFoundException("No stock found for ticker symbol: $tickerSymbol")
        return stock.toDTO()
    }

    fun updateStock(stockTickerSymbol: String, stockDTO: StockDTO): StockDTO {
        val existingStock = stockRepository.findById(stockTickerSymbol)

        return if (existingStock.isPresent) {
            existingStock.get()
                .let {
                    it.companyName = stockDTO.companyName
                    it.price = stockDTO.price
                    it.quantity = stockDTO.quantity
                    stockRepository.save(it)
                    StockDTO(it.tickerSymbol, it.companyName, it.price, it.quantity)
                }
        } else {
            throw StockNotFoundException("No stock found for ticker symbol: $stockTickerSymbol")
        }
    }

    fun deleteStock(stockTickerSymbol: String): Any {
        val existingStock = stockRepository.findById(stockTickerSymbol)

        return if (existingStock.isPresent) {
            existingStock.get().let {
                stockRepository.deleteById(stockTickerSymbol)
            }
        } else {
            throw StockNotFoundException("No stock found for ticker symbol: $stockTickerSymbol")
        }
    }

    //Secondary functions
    @Transactional
    fun buyStock(tickerSymbol: String, stockDTO: StockDTO): StockDTO {
        val existingStock = stockRepository.findByTickerSymbol(tickerSymbol)
            ?: throw StockNotFoundException("No stock found for ticker symbol: $tickerSymbol")

        // Update the existing stock with the new buy price and quantity
        val newTotalQuantity = existingStock.quantity + stockDTO.quantity
        val newAveragePrice = ((existingStock.price * existingStock.quantity) +
                (stockDTO.price * stockDTO.quantity)) / newTotalQuantity

        existingStock.quantity = newTotalQuantity
        existingStock.price = newAveragePrice

        stockRepository.save(existingStock)

        return StockDTO(
            tickerSymbol = existingStock.tickerSymbol,
            companyName = existingStock.companyName,
            price = existingStock.price,
            quantity = existingStock.quantity
        )
    }

    @Transactional
    fun sellStock(tickerSymbol: String, stockDTO: StockDTO): StockDTO{
        val existingStock = stockRepository.findByTickerSymbol(tickerSymbol)
            ?: throw StockNotFoundException("No stock found for ticker symbol: $tickerSymbol")
        // Update the existing stock with the new quantity
        if (stockDTO.quantity > existingStock.quantity){
            throw InsufficientStockQuantityException("You don't have enough stock quantity to sell. Current stock quantity: ${existingStock.quantity}")
        }
        val newTotalQuality = existingStock.quantity - stockDTO.quantity

        existingStock.quantity = newTotalQuality

        stockRepository.save(existingStock)

        return StockDTO(
            tickerSymbol = existingStock.tickerSymbol,
            companyName = existingStock.companyName,
            price = existingStock.price,
            quantity = existingStock.quantity
        )

    }

}

