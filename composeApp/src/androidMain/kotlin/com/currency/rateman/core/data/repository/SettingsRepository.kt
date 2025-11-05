package com.currency.rateman.core.data.repository

import com.currency.rateman.core.domain.model.Settings
import com.currency.rateman.core.domain.model.CurrencyCode
import com.currency.rateman.core.domain.app.LanguageCode
import com.currency.rateman.core.domain.app.ThemeMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getSettings(): Flow<Settings>

    suspend fun editSettings(
        currencyCode: CurrencyCode? = null,
        languageCode: LanguageCode? = null,
        themeMode: ThemeMode? = null
    )

    suspend fun ensureSettingsExist()
}