package com.currency.rateman.core.domain.usecase

import com.currency.rateman.core.domain.model.CurrencyCode
import kotlinx.coroutines.flow.Flow

interface GetAllCurrenciesUseCase {
    fun execute(): Flow<List<CurrencyCode>>
}