package com.currency.rateman.provider.domain.usecase

import com.currency.rateman.provider.domain.repository.ProviderRepository

class GetProviderByIdUseCaseImpl (
    private val providerRepository: ProviderRepository) : GetProviderByIdUseCase {
}