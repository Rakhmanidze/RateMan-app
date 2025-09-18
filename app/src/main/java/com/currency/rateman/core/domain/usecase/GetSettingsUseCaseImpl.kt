package com.currency.rateman.core.domain.usecase

import com.currency.rateman.core.data.model.Settings
import com.currency.rateman.core.data.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetSettingsUseCaseImpl(
    private val settingsRepository: SettingsRepository) : GetSettingsUseCase {
    override suspend fun execute(): Flow<Settings> {
        return settingsRepository.getSettings()
    }
}