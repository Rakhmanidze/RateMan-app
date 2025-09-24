package com.currency.rateman.core.data.model

import com.currency.rateman.core.domain.model.CurrencyCode
import java.time.LocalDate

data class CurrencyRate(
    val foreignCurrency: CurrencyCode,
    val buyRate: Double,
    val sellRate: Double,
    val date: LocalDate
)