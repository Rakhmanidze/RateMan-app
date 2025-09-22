package com.currency.rateman.core.domain.usecase

import com.currency.rateman.core.data.model.enums.CurrencyCode

interface GetAllCurrenciesUseCase {
    fun execute(): List<CurrencyCode>
}