package com.currency.rateman.core.domain.usecase

import com.currency.rateman.core.data.model.CurrencyCode

class FilterCurrenciesUseCaseImpl : FilterCurrenciesUseCase {
    override fun execute(
        query: String,
        currencies: List<CurrencyCode>
    ): List<CurrencyCode> {
        TODO("Not yet implemented")
    }
}