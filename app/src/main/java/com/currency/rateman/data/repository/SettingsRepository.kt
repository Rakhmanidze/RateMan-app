package com.currency.rateman.data.repository

import com.currency.rateman.data.model.enums.CurrencyCode
import com.currency.rateman.data.model.enums.LanguageCode
import com.currency.rateman.data.model.Settings
import com.currency.rateman.data.model.enums.ThemeMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getSettings(): Flow<Settings>
    suspend fun saveSettings(settings: Settings)
    suspend fun editSettings(
        currencyCode: CurrencyCode? = null,
        languageCode: LanguageCode? = null,
        themeMode: ThemeMode? = null
    )
    suspend fun resetSettings()
    suspend fun ensureSettingsExist()
}