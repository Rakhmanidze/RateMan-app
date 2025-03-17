package com.example.rateman

import com.example.rateman.ui.theme.CurrencyCode

data class CurrencyRate(
    val foreignCurrency: CurrencyCode,
    val buyRate: Double,
    val sellRate: Double
)
