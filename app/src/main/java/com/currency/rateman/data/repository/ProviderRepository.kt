package com.currency.rateman.data.repository

import com.currency.rateman.api.RateProviderAPI
import com.currency.rateman.data.model.Provider
import kotlinx.coroutines.flow.Flow

interface ProviderRepository {

    fun getAllProviders() : Flow<List<Provider>>

    suspend fun getProviderById(id: Long) : Provider?

    suspend fun insertProvider(provider: Provider) : Long

    suspend fun insertApiProviders(apiProviders: List<RateProviderAPI>)
}