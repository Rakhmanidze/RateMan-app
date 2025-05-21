package com.currency.rateman.data.model

import com.currency.rateman.data.model.enums.CurrencyCode
import java.time.LocalDate

data class CurrencyRate(
    val foreignCurrency: CurrencyCode,
    val buyRate: Double,
    val sellRate: Double,
    val date: LocalDate
)