package com.currency.rateman.core.domain.usecase

import com.currency.shared.core.domain.model.CurrencyCode

interface FilterCurrenciesUseCase {
    fun execute(query: String, currencies: List<CurrencyCode>): List<CurrencyCode>
}