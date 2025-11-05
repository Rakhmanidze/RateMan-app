package com.currency.rateman.provider.domain.repository

import com.currency.rateman.api.kurzyCz.ProviderAPI
import com.currency.rateman.provider.domain.model.Provider
import kotlinx.coroutines.flow.Flow

interface ProviderRepository {
    fun getAllProviders() : Flow<List<Provider>>

    suspend fun getProviderById(id: Long) : Provider?

    suspend fun insertProvider(provider: Provider) : Long

    suspend fun insertApiProviders(apiProviders: List<ProviderAPI>)

    suspend fun refreshJindrisskaExchangeRates()

    suspend fun refreshEuroChangeRates()
    suspend fun refreshCernaRuzeExchangeRates()
}