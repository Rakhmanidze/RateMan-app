package com.currency.rateman.api.kurzyCz

import com.currency.rateman.provider.domain.repository.ProviderRepository
import com.currency.rateman.provider.utils.ProviderConstants
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object RateFetcher : KoinComponent {
    private val providerRepository: ProviderRepository by inject()

    suspend fun fetchAndStoreRates() {
        try {
            val response: List<ProviderAPI> = APIClient.getRates("json/meny/b%5B-1%5D.json")

            val filteredProviders = response
                .filter { !ProviderConstants.EXCLUDED_PROVIDERS.contains(it.banka) }
                .map { provider ->
                    if (provider.banka == "Exchange VIP") {
                        provider.copy(banka = "Směnárna Štefánikova")
                    } else {
                        provider
                    }
                }
            providerRepository.insertApiProviders(filteredProviders)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}