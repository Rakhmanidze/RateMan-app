package com.currency.rateman.core.domain.usecase

import com.currency.rateman.core.data.model.enums.CurrencyCode

class GetAllCurrenciesUseCaseImpl() : GetAllCurrenciesUseCase {
    override fun execute(): List<CurrencyCode> {
        return CurrencyCode.entries.toList()
    }
}