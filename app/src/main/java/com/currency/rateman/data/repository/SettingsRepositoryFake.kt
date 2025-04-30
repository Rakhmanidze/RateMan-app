package com.currency.rateman.data.repository

import com.currency.rateman.data.model.CurrencyCode
import com.currency.rateman.data.model.LanguageCode
import com.currency.rateman.data.model.Settings
import com.currency.rateman.data.model.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsRepositoryFake : SettingsRepository {
    private val settingsFlow = MutableStateFlow(
        Settings(
            defaultCurrency = CurrencyCode.CZK,
            uiLanguage = LanguageCode.EN,
            themeMode = ThemeMode.DARK
        )
    )

    override fun getSettings(): Flow<Settings> = settingsFlow.asStateFlow()

    override suspend fun saveSettings(settings: Settings) {
        settingsFlow.value = settings
    }

    override suspend fun resetSettings() {
        settingsFlow.value = Settings(
            defaultCurrency = CurrencyCode.CZK,
            uiLanguage = LanguageCode.EN,
            themeMode = ThemeMode.DARK
        )
    }

    override suspend fun editSettings(
        currencyCode: CurrencyCode?,
        languageCode: LanguageCode?,
        themeMode: ThemeMode?
    ) {
        TODO("Not yet implemented")
    }
}