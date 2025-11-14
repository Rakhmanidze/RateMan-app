package com.currency.rateman.api

import com.currency.rateman.provider.domain.repository.ProviderRepository
import com.currency.rateman.provider.utils.ProviderConstants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.http.ContentType

class RateFetcher(
    private val httpClient: HttpClient,
    private val providerRepository: ProviderRepository
) {
    suspend fun fetchAndStoreRates() {
        try {
            val response: List<ProviderAPI> = httpClient.get("https://data.kurzy.cz/json/meny/b%5B-1%5D.json") {
                accept(ContentType.Application.Json)
            }.body()

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