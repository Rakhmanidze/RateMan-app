package com.currency.rateman.core.domain.usecase

import com.currency.rateman.core.domain.model.CurrencyCode

class FilterCurrenciesUseCaseImpl : FilterCurrenciesUseCase {
    override fun execute(
        query: String,
        currencies: List<CurrencyCode>
    ): List<CurrencyCode> {
        val filteredCurrencies = currencies.filter {
            it != CurrencyCode.CZK
        }
        return if (query.isBlank()) {
            filteredCurrencies
        } else {
            filteredCurrencies.filter { currency ->
                currency.name.contains(query, ignoreCase = true)
            }
        }
    }
}