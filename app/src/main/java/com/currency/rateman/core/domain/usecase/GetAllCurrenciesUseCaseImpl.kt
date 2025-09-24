package com.currency.rateman.core.domain.usecase

import com.currency.rateman.core.domain.model.CurrencyCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetAllCurrenciesUseCaseImpl : GetAllCurrenciesUseCase {
    override fun execute(): Flow<List<CurrencyCode>> {
        return flowOf(CurrencyCode.entries.toList())
    }
}