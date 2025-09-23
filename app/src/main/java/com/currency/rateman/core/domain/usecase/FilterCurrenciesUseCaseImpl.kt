package com.currency.rateman.core.domain.usecase

import com.currency.rateman.core.data.model.CurrencyCode

class FilterCurrenciesUseCaseImpl : FilterCurrenciesUseCase {
    override fun execute(
        query: String,
        currencies: List<CurrencyCode>
    ): List<CurrencyCode> {
        return if (query.isBlank()) {
            currencies
        } else {
            currencies.filter { currency ->
                currency.name.contains(query, ignoreCase = true)
            }
        }
    }
}