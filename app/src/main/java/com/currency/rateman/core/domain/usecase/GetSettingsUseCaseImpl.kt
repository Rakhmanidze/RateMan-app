package com.currency.rateman.core.domain.usecase

import com.currency.rateman.core.data.model.Settings
import com.currency.rateman.core.data.repository.SettingsRepository

class GetSettingsUseCaseImpl(
    private val settings: SettingsRepository) : GetSettingsUseCase {
    override suspend fun execute(): Settings? {
        TODO("Not yet implemented")
    }
}