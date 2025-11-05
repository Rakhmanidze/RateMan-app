package com.currency.rateman.core.domain.model

import com.currency.shared.core.domain.model.CurrencyCode
import java.time.LocalDate

data class CurrencyRate(
    val foreignCurrency: CurrencyCode,
    val buyRate: Double,
    val sellRate: Double,
    val date: LocalDate
)