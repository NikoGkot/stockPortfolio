package com.kotlinspring.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name="stocks")
data class Stock (
        @Id
        val tickerSymbol: String
)
{
override fun toString(): String{
        return "Stock(ticker symbol='$tickerSymbol')"
}
}