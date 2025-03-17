package com.currency.rateman.data.model

data class CurrencyRate(
    private val foreignCurrency: CurrencyCode,
    private val buyRate: Double,
    private val sellRate: Double
) {

    fun getForeignCurrency(): CurrencyCode {
        return foreignCurrency
    }

    fun getBuyRate(): Double {
        return buyRate
    }
    fun getSellRate(): Double {
        return sellRate
    }
}