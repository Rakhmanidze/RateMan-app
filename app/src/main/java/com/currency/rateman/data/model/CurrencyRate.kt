package com.currency.rateman.data.model

data class CurrencyRate(
    val foreignCurrency: CurrencyCode,
    val buyRate: Double,
    val sellRate: Double
)