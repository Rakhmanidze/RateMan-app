package com.currency.rateman.provider.domain.usecase

import com.currency.rateman.provider.data.model.Provider
import kotlinx.coroutines.flow.Flow

interface GetProviderByIdUseCase {
    fun execute(): Flow<Provider>
}