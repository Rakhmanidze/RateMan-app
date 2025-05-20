package com.currency.rateman.data.repository

import com.currency.rateman.api.RateProviderAPI
import com.currency.rateman.data.model.ProviderType
import com.currency.rateman.data.model.RateProvider
import kotlinx.coroutines.flow.Flow

interface RateProviderRepository {
    fun getAllProviders() : Flow<List<RateProvider>>

    fun getProvidersByType(providerType: ProviderType) : Flow<List<RateProvider>>

    suspend fun getProviderById(id: Long) : RateProvider?

    suspend fun insertProvider(provider: RateProvider) : Long

    suspend fun deleteProviders(ids: List<Long>)

    suspend fun insertApiProviders(apiProviders: List<RateProviderAPI>)
}