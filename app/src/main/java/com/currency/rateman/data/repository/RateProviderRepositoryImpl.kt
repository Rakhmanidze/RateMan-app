package com.currency.rateman.data.repository

import com.currency.rateman.data.db.dao.CurrencyRateDao
import com.currency.rateman.data.db.dao.RateProviderDao
import com.currency.rateman.data.model.ProviderType
import com.currency.rateman.data.model.RateProvider
import com.currency.rateman.api.RateProviderConverter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import com.currency.rateman.api.RateProviderAPI
import com.currency.rateman.data.db.entity.CurrencyRateEntity
import com.currency.rateman.data.db.entity.RateProviderEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.stateIn

class RateProviderRepositoryImpl (
    private val rateProviderDao: RateProviderDao,
    private val currencyRateDao: CurrencyRateDao
) : RateProviderRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun getAllProviders(): Flow<List<RateProvider>> {
        return rateProviderDao.getAllProviders().map { providerEntities ->
            providerEntities.map { entity ->
                val rates = currencyRateDao.getRatesForProvider(entity.id)
                    .map { rates -> rates }
                    .stateIn(coroutineScope, SharingStarted.Lazily, emptyList())
                    .value
                RateProviderConverter.toRateProvider(entity, rates)
            }
        }
    }

    override fun getProvidersByType(providerType: ProviderType): Flow<List<RateProvider>> {
        return rateProviderDao.getProvidersByType(providerType.name).map { entities ->
            entities.map { entity ->
                val rates = currencyRateDao.getRatesForProvider(entity.id)
                    .map { rates -> rates }
                    .stateIn(coroutineScope, SharingStarted.Lazily, emptyList())
                    .value
                RateProviderConverter.toRateProvider(entity, rates)
            }
        }
    }

    override suspend fun getProviderById(id: Long): RateProvider? {
        val entity = rateProviderDao.getProviderById(id) ?: return null
        val rates = currencyRateDao.getRatesForProvider(id)
            .map { rates -> rates }
            .stateIn(coroutineScope, SharingStarted.Lazily, emptyList())
            .value
        return RateProviderConverter.toRateProvider(entity, rates)
    }

    override suspend fun insertProvider(provider: RateProvider): Long {
        val entity = RateProviderEntity(
            name = provider.name,
            baseCurrency = provider.baseCurrency.name,
            phoneNumber = provider.phoneNumber,
            type = provider.type.name
        )
        val providerId = rateProviderDao.insertProvider(entity)
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

    override suspend fun deleteProviders(ids: List<Long>) {
        ids.forEach { id ->
            currencyRateDao.deleteRatesForProvider(id)
            rateProviderDao.deleteProvider(RateProviderEntity(id = id))
        }
    }

    override suspend fun insertApiProviders(apiProviders: List<RateProviderAPI>) {
        apiProviders.forEach { apiProvider ->
            val existingProvider = rateProviderDao.getProviderById(apiProvider.banka.hashCode().toLong())
            val providerId = existingProvider?.id
                ?: rateProviderDao.insertProvider(
                    RateProviderConverter.toRateProviderEntity(apiProvider)
                )
            val rateEntities = RateProviderConverter.toCurrencyRateEntities(apiProvider, providerId)
            currencyRateDao.insertAllRates(rateEntities)
        }
    }
}