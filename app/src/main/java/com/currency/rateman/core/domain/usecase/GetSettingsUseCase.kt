package com.currency.rateman.core.domain.usecase

import com.currency.rateman.core.data.model.Settings
import kotlinx.coroutines.flow.Flow

interface GetSettingsUseCase {
    suspend fun execute(): Flow<Settings>
}