package com.currency.rateman.provider.domain.usecase

import com.currency.rateman.provider.data.model.Provider
import com.currency.rateman.provider.domain.repository.ProviderRepository
import kotlinx.coroutines.flow.Flow

class GetProviderByIdUseCaseImpl (
    private val providerRepository: ProviderRepository) : GetProviderByIdUseCase {
    override fun execute(): Flow<Provider> {
        TODO("Not yet implemented")
    }
}