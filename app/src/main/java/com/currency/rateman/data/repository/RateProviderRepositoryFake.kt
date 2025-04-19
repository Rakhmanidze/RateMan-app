package com.currency.rateman.data.repository

import com.currency.rateman.data.model.CurrencyCode
import com.currency.rateman.data.model.CurrencyRate
import com.currency.rateman.data.model.ProviderType
import com.currency.rateman.data.model.RateProvider
import java.time.LocalDate

class RateProviderRepositoryFake : RateProviderRepository  {
    private val providers = mutableListOf<RateProvider>()

    init {
        // Add some initial test providers
        providers.addAll(listOf(
            RateProvider(
                name = "Komerční Banka",
                baseCurrency = CurrencyCode.CZK,
                rates = listOf(
                    CurrencyRate(CurrencyCode.USD, 22.5, 23.0, LocalDate.of(2025, 4, 19)),
                    CurrencyRate(CurrencyCode.EUR, 24.0, 24.5, LocalDate.of(2025, 4, 19))
                ),
                phoneNumber = "+420 800 111 222",
                type = ProviderType.BANK
            ),
            RateProvider(
                name = "Exchange Praha",
                baseCurrency = CurrencyCode.CZK,
                rates = listOf(
                    CurrencyRate(CurrencyCode.USD, 22.7, 23.2, LocalDate.of(2025, 4, 19)),
                    CurrencyRate(CurrencyCode.EUR, 24.2, 24.7, LocalDate.of(2025, 4, 19))
                ),
                phoneNumber = "+420 222 333 444",
                type = ProviderType.EXCHANGE
            )
        ))
    }


    override fun getAllProviders(): List<RateProvider> {
        return providers.toList()
    }

    override fun getProvidersByType(providerType: ProviderType): List<RateProvider> {
        return providers.filter { it.type == providerType }
    }

    override fun getProviderById(id: Long): RateProvider? {
        return if (id >= 0 && id < providers.size) providers[id.toInt()] else null
    }

    override suspend fun insertProvider(provider: RateProvider): Long {
        providers.add(provider)
        return (providers.size - 1).toLong()
    }

    override suspend fun deleteProviders(ids: List<Long>) {
        val indices = ids.map { it.toInt() }.filter { it >= 0 && it < providers.size }
        indices.sortedDescending().forEach { providers.removeAt(it) }
    }
}