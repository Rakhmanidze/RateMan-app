package com.currency.rateman.provider.domain.usecase

import com.currency.rateman.provider.data.model.Provider

interface GetProviderByIdUseCase {
    suspend fun execute(id: Long): Provider?
}