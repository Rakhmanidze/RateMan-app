package com.currency.rateman.provider.domain.usecase

import com.currency.rateman.core.data.model.Filter
import com.currency.rateman.core.data.model.CurrencyCode
import com.currency.rateman.provider.data.model.ProviderType
import com.currency.rateman.core.data.model.RateSortType
import com.currency.rateman.provider.data.model.Provider

class FilterProvidersUseCaseImpl() : FilterProvidersUseCase {
    override suspend fun execute(
        providers: List<Provider>,
        searchQuery: String,
        filter: Filter?
    ): List<Provider> {
        var filteredProviders = providers

        filteredProviders = applySearchFilter(filteredProviders, searchQuery)

        filteredProviders = applyProviderTypeFilter(filteredProviders, filter?.selectedProviderType)

        filteredProviders = applyCurrencyFilterAndSort(filteredProviders, filter)

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

    private fun applyCurrencyFilterAndSort(
        providers: List<Provider>,
        filter: Filter?
    ): List<Provider> {
        val targetCurrency = filter?.targetCurrency ?: return providers

        val currencyFilteredProviders = providers.filter { provider ->
            provider.rates.any { rate -> rate.foreignCurrency == filter.targetCurrency }
        }

        return when (filter.selectedRateSortType) {
            RateSortType.BEST_SELL -> sortBySellRate(currencyFilteredProviders, targetCurrency)
            RateSortType.BEST_BUY -> sortByBuyRate(currencyFilteredProviders, targetCurrency)
            RateSortType.BEST_RATE -> currencyFilteredProviders.sortedBy { it.name }
        }
    }

    private fun sortBySellRate(providers: List<Provider>, currency: CurrencyCode): List<Provider> {
        return providers.sortedByDescending { provider ->
            provider.rates
                .firstOrNull { it.foreignCurrency == currency }
                ?.sellRate ?: Double.POSITIVE_INFINITY
        }
    }

    private fun sortByBuyRate(providers: List<Provider>, currency: CurrencyCode): List<Provider> {
        return providers.sortedBy { provider ->
            provider.rates
                .firstOrNull { it.foreignCurrency == currency }
                ?.sellRate ?: Double.NEGATIVE_INFINITY
        }
    }
}