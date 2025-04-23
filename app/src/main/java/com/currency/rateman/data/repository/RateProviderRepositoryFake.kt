package com.currency.rateman.data.repository

import com.currency.rateman.data.model.CurrencyCode
import com.currency.rateman.data.model.CurrencyRate
import com.currency.rateman.data.model.ProviderType
import com.currency.rateman.data.model.RateProvider
import java.time.LocalDate

class RateProviderRepositoryFake : RateProviderRepository  {
    private val providers = mutableListOf<RateProvider>()

    init {
        providers.addAll(
            listOf(
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
                ),
                RateProvider(
                    name = "Česká Spořitelna",
                    baseCurrency = CurrencyCode.CZK,
                    rates = listOf(
                        CurrencyRate(CurrencyCode.USD, 22.4, 22.9, LocalDate.of(2025, 4, 19)),
                        CurrencyRate(CurrencyCode.EUR, 23.9, 24.4, LocalDate.of(2025, 4, 19))
                    ),
                    phoneNumber = "+420 800 207 207",
                    type = ProviderType.BANK
                ),
                RateProvider(
                    name = "Směnárna Centrum",
                    baseCurrency = CurrencyCode.CZK,
                    rates = listOf(
                        CurrencyRate(CurrencyCode.USD, 22.6, 23.1, LocalDate.of(2025, 4, 19)),
                        CurrencyRate(CurrencyCode.EUR, 24.1, 24.6, LocalDate.of(2025, 4, 19))
                    ),
                    phoneNumber = "+420 777 123 456",
                    type = ProviderType.EXCHANGE
                ),
                RateProvider(
                    name = "Moneta Money Bank",
                    baseCurrency = CurrencyCode.CZK,
                    rates = listOf(
                        CurrencyRate(CurrencyCode.USD, 22.3, 22.8, LocalDate.of(2025, 4, 19)),
                        CurrencyRate(CurrencyCode.EUR, 23.8, 24.3, LocalDate.of(2025, 4, 19))
                    ),
                    phoneNumber = "+420 224 443 636",
                    type = ProviderType.BANK
                ),
                RateProvider(
                    name = "VIP Exchange",
                    baseCurrency = CurrencyCode.CZK,
                    rates = listOf(
                        CurrencyRate(CurrencyCode.USD, 22.8, 23.3, LocalDate.of(2025, 4, 19)),
                        CurrencyRate(CurrencyCode.EUR, 24.3, 24.8, LocalDate.of(2025, 4, 19))
                    ),
                    phoneNumber = "+420 602 555 777",
                    type = ProviderType.EXCHANGE
                ),
                RateProvider(
                    name = "Raiffeisenbank",
                    baseCurrency = CurrencyCode.CZK,
                    rates = listOf(
                        CurrencyRate(CurrencyCode.USD, 22.6, 23.0, LocalDate.of(2025, 4, 19)),
                        CurrencyRate(CurrencyCode.EUR, 24.0, 24.5, LocalDate.of(2025, 4, 19))
                    ),
                    phoneNumber = "+420 800 900 900",
                    type = ProviderType.BANK
                )
            )
        )
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