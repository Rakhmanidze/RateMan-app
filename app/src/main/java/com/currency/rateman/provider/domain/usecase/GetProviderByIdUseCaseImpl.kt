package com.currency.rateman.provider.domain.usecase

import com.currency.rateman.provider.domain.model.Provider
import com.currency.rateman.provider.domain.repository.ProviderRepository

class GetProviderByIdUseCaseImpl(private val providerRepository: ProviderRepository) : GetProviderByIdUseCase {
    override suspend fun execute(id: Long): Provider? {
        return providerRepository.getProviderById(id)
    }
}