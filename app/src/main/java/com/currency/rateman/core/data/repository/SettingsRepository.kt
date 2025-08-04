package com.currency.rateman.core.data.repository

import com.currency.rateman.core.data.model.Settings
import com.currency.rateman.core.data.model.enums.CurrencyCode
import com.currency.rateman.core.data.model.enums.LanguageCode
import com.currency.rateman.core.data.model.enums.ThemeMode
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