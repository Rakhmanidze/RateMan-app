package com.currency.rateman.provider.domain.usecase

import com.currency.rateman.core.data.model.Filter
import com.currency.rateman.provider.data.model.Provider

interface FilterProvidersUseCase {
    suspend fun execute(
        providers: List<Provider>,
        searchQuery: String,
        filter: Filter?
    ): List<Provider>
}