package com.currency.rateman.provider.data.repository

import com.currency.rateman.api.kurzyCz.ProviderAPI
import com.currency.rateman.api.kurzyCz.ProviderConverter
import com.currency.rateman.api.scraper.ExchangeRate
import com.currency.rateman.api.scraper.fetchAlfaPragueRates
import com.currency.rateman.api.scraper.fetchAuraAktivRates
import com.currency.rateman.api.scraper.fetchCernaRuzeExchangeRates
import com.currency.rateman.api.scraper.fetchEuroChangeRates
import com.currency.rateman.api.scraper.fetchExchange8Rates
import com.currency.rateman.api.scraper.fetchJindrisskaExchangeRates
import com.currency.rateman.api.scraper.fetchRoyalExchangeRates
import com.currency.rateman.api.scraper.fetchTopExchangeRates
import com.currency.rateman.core.data.dao.CurrencyRateDao
import com.currency.rateman.core.data.entity.CurrencyRateEntity
import com.currency.rateman.provider.data.model.ProviderEntity
import com.currency.rateman.core.domain.model.CurrencyRate
import com.currency.rateman.core.domain.model.CurrencyCode
import com.currency.rateman.provider.data.model.ProviderType
import com.currency.rateman.provider.data.dao.ProviderDao
import com.currency.rateman.provider.domain.model.Provider
import com.currency.rateman.provider.domain.repository.ProviderRepository
import com.currency.rateman.provider.utils.ProviderConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class ProviderRepositoryImpl (
    private val providerDao: ProviderDao,
    private val currencyRateDao: CurrencyRateDao
) : ProviderRepository {
    override fun getAllProviders(): Flow<List<Provider>> {
        return providerDao.getAllProviders().map { providerEntities ->
            providerEntities.map { entity ->
                val rates = currencyRateDao.getRatesForProvider(entity.id).first()
                ProviderConverter.toProvider(entity, rates)
            }
        }
    }

    override suspend fun getProviderById(id: Long): Provider? {
        val entity = providerDao.getProviderById(id) ?: return null
        val rates = currencyRateDao.getRatesForProvider(id).first()
        return ProviderConverter.toProvider(entity, rates)
    }

    override suspend fun insertProvider(provider: Provider): Long {
        val entity = ProviderEntity(
            name = provider.name,
            baseCurrency = provider.baseCurrency.name,
            phoneNumber = provider.phoneNumber,
            type = provider.type.name
        )
        val providerId = providerDao.insertProvider(entity)
        val rateEntities = provider.rates.map { rate ->
            CurrencyRateEntity(
                providerId = providerId,
                foreignCurrency = rate.foreignCurrency.name,
                buyRate = rate.buyRate,
                sellRate = rate.sellRate,
                date = rate.date.toString()
            )
        }
        currencyRateDao.insertAllRates(rateEntities)
        return providerId
    }

    override suspend fun insertApiProviders(apiProviders: List<ProviderAPI>) {
        val providerEntities = apiProviders.map { apiProvider ->
            ProviderConverter.toProviderEntity(apiProvider)
        }
        val providerIds = providerDao.insertAllProvidersAndReturnIds(providerEntities)

        val ratesToInsert = apiProviders.flatMapIndexed { index, apiProvider ->
            ProviderConverter.toCurrencyRateEntities(apiProvider, providerIds[index])
        }
        currencyRateDao.insertAllRates(ratesToInsert)
    }

    private suspend fun refreshRates(
        fetchRates: suspend () -> List<ExchangeRate>,
        providerName: String
    ) {
        val exchangeRates = fetchRates()
        if (exchangeRates.isEmpty()) return

        val rates = exchangeRates.mapNotNull { er ->
            val buy = er.weBuy.replace(",", ".").toDoubleOrNull()
            val sell = er.weSell.replace(",", ".").toDoubleOrNull()

            if (buy != null && sell != null) {
                CurrencyRate(
                    foreignCurrency = CurrencyCode.valueOf(er.currency),
                    buyRate = buy,
                    sellRate = sell,
                    date = LocalDate.now()
                )
            } else null
        }

        val provider = Provider(
            id = 0,
            name = providerName,
            baseCurrency = CurrencyCode.CZK,
            phoneNumber = "",
            type = ProviderType.EXCHANGE,
            rates = rates
        )
        insertProvider(provider)
    }

    override suspend fun refreshTopExchangeRates() =
        refreshRates(::fetchTopExchangeRates, ProviderConstants.Scraped_providers.TOP_EXCHANGE)

    override suspend fun refreshAlfaPragueRates() =
        refreshRates(::fetchAlfaPragueRates, ProviderConstants.Scraped_providers.ALFA_PRAGUE)

    override suspend fun refreshJindrisskaExchangeRates() =
        refreshRates(::fetchJindrisskaExchangeRates, ProviderConstants.Scraped_providers.JINDRISSKA_EXCHANGE)

    override suspend fun refreshEuroChangeRates() =
        refreshRates(::fetchEuroChangeRates, ProviderConstants.Scraped_providers.EURO_CHANGE)

    override suspend fun refreshRoyalExchangeRates() =
        refreshRates(::fetchRoyalExchangeRates, ProviderConstants.Scraped_providers.ROYAL_EXCHANGE)

    override suspend fun refreshCernaRuzeExchangeRates() =
        refreshRates(::fetchCernaRuzeExchangeRates, ProviderConstants.Scraped_providers.CERNA_RUZE_EXCHANGE)

    override suspend fun refreshExchange8Rates() =
        refreshRates(::fetchExchange8Rates, ProviderConstants.Scraped_providers.EXCHANGE8)

    override suspend fun refreshAuraAktivRates() =
        refreshRates(::fetchAuraAktivRates, ProviderConstants.Scraped_providers.AURA_AKTIV)
}