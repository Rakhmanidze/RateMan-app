package com.currency.rateman.core.domain.repository

import com.currency.rateman.core.domain.app.LanguageCode
import com.currency.rateman.core.domain.model.CurrencyCode
import com.currency.rateman.core.domain.model.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getSettings(): Flow<Settings>

    suspend fun editSettings(
        currencyCode: CurrencyCode? = null,
        languageCode: LanguageCode? = null
    )

    suspend fun ensureSettingsExist()
}