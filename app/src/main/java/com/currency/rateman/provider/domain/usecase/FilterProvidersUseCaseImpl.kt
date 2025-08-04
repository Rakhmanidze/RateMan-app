package com.currency.rateman.provider.domain.usecase

import com.currency.rateman.core.data.model.Filter
import com.currency.rateman.provider.data.model.Provider
import com.currency.rateman.provider.domain.repository.ProviderRepository

class FilterProvidersUseCaseImpl(
    private val providerRepository: ProviderRepository) : FilterProvidersUseCase {
    override suspend fun execute(
        providers: List<Provider>,
        searchQuery: String,
        filter: Filter?
    ): List<Provider> {
        TODO("Not yet implemented")
    }

}