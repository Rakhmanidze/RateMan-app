package com.currency.rateman.provider.domain.usecase

import com.currency.rateman.core.data.model.Filter
import com.currency.rateman.core.data.model.enums.ProviderType
import com.currency.rateman.provider.data.model.Provider
import com.currency.rateman.provider.domain.repository.ProviderRepository

class FilterProvidersUseCaseImpl(
    private val providerRepository: ProviderRepository) : FilterProvidersUseCase {
    override suspend fun execute(
        providers: List<Provider>,
        searchQuery: String,
        filter: Filter?
    ): List<Provider> {
        var filteredProviders = providers

        filteredProviders = applySearchFilter(filteredProviders, searchQuery)

        filteredProviders = applyProviderTypeFilter(filteredProviders, filter?.selectedProviderType)

        return filteredProviders
    }

    private fun applySearchFilter(providers: List<Provider>, searchQuery: String): List<Provider> {
        return if (searchQuery.isBlank()) {
            providers
        } else {
            providers.filter { provider ->
                provider.name.contains(searchQuery.trim(), ignoreCase = true)
            }
        }
    }

    private fun applyProviderTypeFilter(
        providers: List<Provider>,
        providerType: ProviderType?
    ): List<Provider> {
        return when (providerType) {
            null, ProviderType.ALL -> providers
            ProviderType.BANK -> providers.filter { it.type == ProviderType.BANK }
            ProviderType.EXCHANGE -> providers.filter { it.type == ProviderType.EXCHANGE }
            ProviderType.CRYPTO_EXCHANGE -> providers.filter { it.type == ProviderType.CRYPTO_EXCHANGE }
        }
    }


}