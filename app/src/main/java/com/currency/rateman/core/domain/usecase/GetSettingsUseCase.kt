package com.currency.rateman.core.domain.usecase

import com.currency.rateman.core.data.model.Settings

interface GetSettingsUseCase {
    suspend fun execute(): Settings?
}