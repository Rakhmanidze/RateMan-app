package com.currency.rateman.provider.domain.usecase

import com.currency.rateman.provider.data.model.Provider
import com.currency.rateman.provider.domain.repository.ProviderRepository
import kotlinx.coroutines.flow.Flow

class GetAllProviderUseCaseImpl (
    private val providerRepository: ProviderRepository) : GetAllProviderUseCase {
    override fun execute(): Flow<List<Provider>> {
        return providerRepository.getAllProviders()
    }
}