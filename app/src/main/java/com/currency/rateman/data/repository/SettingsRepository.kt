package com.currency.rateman.data.repository

import com.currency.rateman.data.model.CurrencyCode
import com.currency.rateman.data.model.LanguageCode
import com.currency.rateman.data.model.Settings
import com.currency.rateman.data.model.ThemeMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getSettings(): Flow<Settings>
    suspend fun saveSettings(settings: Settings)
    suspend fun resetSettings()
    suspend fun editSettings(
        currencyCode: CurrencyCode? = null,
        languageCode: LanguageCode? = null,
        themeMode: ThemeMode? = null
    )
    suspend fun ensureSettingsExist()
}