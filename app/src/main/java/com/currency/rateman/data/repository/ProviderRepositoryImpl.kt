package com.currency.rateman.data.repository

import com.currency.rateman.data.db.dao.CurrencyRateDao
import com.currency.rateman.data.db.dao.ProviderDao
import com.currency.rateman.data.model.Provider
import com.currency.rateman.api.ProviderConverter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.currency.rateman.api.ProviderAPI
import com.currency.rateman.data.db.entity.CurrencyRateEntity
import com.currency.rateman.data.db.entity.ProviderEntity
import kotlinx.coroutines.flow.first
import com.currency.rateman.apiSingle.fetchTopExchangeRates
import com.currency.rateman.data.model.CurrencyRate
import com.currency.rateman.data.model.enums.CurrencyCode
import com.currency.rateman.data.model.enums.ProviderType
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

    override suspend fun refreshTopExchangeRates() {
        // Call your Jsoup scraper API
        val exchangeRates = fetchTopExchangeRates()

        if (exchangeRates.isEmpty()) return

        // Convert ExchangeRate to your domain model
        // Example: create Provider and rates from scraped data

        val providerName = "Top Exchange" // name of this provider

        // Map ExchangeRate to your domain Rate model (assuming)
        val rates = exchangeRates.mapNotNull { er ->
            // Parse buy/sell rates as Double, ignore if invalid
            val buy = er.weBuy.replace(",", ".").toDoubleOrNull()
            val sell = er.weSell.replace(",", ".").toDoubleOrNull()

            if (buy != null && sell != null) {
                // Assuming your Rate data class has:
                // foreignCurrency: CurrencyCode, buyRate: Double, sellRate: Double, date: String
                CurrencyRate(
                    foreignCurrency = CurrencyCode.valueOf(er.currency),
                    buyRate = buy,
                    sellRate = sell,
                    date = LocalDate.now()
                )
            } else null
        }

        // Create Provider domain object
        val provider = Provider(
            id = 0, // will be auto-generated
            name = providerName,
            baseCurrency = CurrencyCode.CZK, // assuming CZK base currency
            phoneNumber = "", // if no phone number available
            type = ProviderType.EXCHANGE, // or whatever type enum you have
            rates = rates
        )

        // Insert provider and rates (you may want to delete old data for this provider first)
        // For simplicity, just insert for now:
        insertProvider(provider)
    }
}