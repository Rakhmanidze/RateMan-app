package com.currency.rateman.data.repository

import com.currency.rateman.data.db.dao.CurrencyRateDao
import com.currency.rateman.data.db.dao.ProviderDao
import com.currency.rateman.data.model.Provider
import com.currency.rateman.api.RateProviderConverter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.currency.rateman.api.RateProviderAPI
import com.currency.rateman.data.db.entity.CurrencyRateEntity
import com.currency.rateman.data.db.entity.ProviderEntity
import kotlinx.coroutines.flow.first

class ProviderRepositoryImpl (
    private val providerDao: ProviderDao,
    private val currencyRateDao: CurrencyRateDao
) : ProviderRepository {

    override fun getAllProviders(): Flow<List<Provider>> {
        return providerDao.getAllProviders().map { providerEntities ->
            providerEntities.map { entity ->
                val rates = currencyRateDao.getRatesForProvider(entity.id).first()
                RateProviderConverter.toRateProvider(entity, rates)
            }
        }
    }

    override suspend fun getProviderById(id: Long): Provider? {
        val entity = providerDao.getProviderById(id) ?: return null
        val rates = currencyRateDao.getRatesForProvider(id).first()
        return RateProviderConverter.toRateProvider(entity, rates)
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

    override suspend fun insertApiProviders(apiProviders: List<RateProviderAPI>) {
        val providerEntities = apiProviders.map { apiProvider ->
            RateProviderConverter.toRateProviderEntity(apiProvider)
        }
        val providerIds = providerDao.insertAllProvidersAndReturnIds(providerEntities)

        val ratesToInsert = apiProviders.flatMapIndexed { index, apiProvider ->
            RateProviderConverter.toCurrencyRateEntities(apiProvider, providerIds[index])
        }
        currencyRateDao.insertAllRates(ratesToInsert)
    }
}