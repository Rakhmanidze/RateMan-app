package com.currency.rateman.data.repository

import com.currency.rateman.data.model.ProviderType
import com.currency.rateman.data.model.RateProvider

interface RateProviderRepository {
    fun getAllProviders() : List<RateProvider>

    fun getProvidersByType(providerType: ProviderType) : List<RateProvider>

    fun getProviderById(id: Long) : RateProvider?

    suspend fun insertProvider(provider: RateProvider) : Long

    suspend fun deleteProviders(ids: List<Long>)
}