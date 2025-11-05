package com.currency.rateman.core.domain.model

import kotlinx.datetime.LocalDate

data class CurrencyRate(
    val foreignCurrency: CurrencyCode,
    val buyRate: Double,
    val sellRate: Double,
    val date: LocalDate
)