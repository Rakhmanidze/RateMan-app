package com.currency.rateman.data.repository

import com.currency.rateman.data.db.dao.SettingsDao
import com.currency.rateman.data.model.CurrencyCode
import com.currency.rateman.data.model.LanguageCode
import com.currency.rateman.data.model.Settings
import com.currency.rateman.data.model.ThemeMode
import com.currency.rateman.data.model.toEntity
import com.currency.rateman.data.model.toSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(private val settingsDao: SettingsDao) : SettingsRepository{
    override fun getSettings(): Flow<Settings> {
        return settingsDao.getSettingsById(0).map { entity ->
            if ( entity == null ) {
                val defaultSettings = Settings(
                    defaultCurrency = CurrencyCode.CZK,
                    uiLanguage = LanguageCode.EN,
                    themeMode = ThemeMode.DARK
                )
                settingsDao.insertSettings(defaultSettings.toEntity())
                defaultSettings
            } else {
                entity.toSettings()
            }
        }
    }

    override suspend fun saveSettings(settings: Settings) {
        settingsDao.insertSettings(settings.toEntity())
    }

    override suspend fun resetSettings() {
        settingsDao.deleteAllSettings()

        settingsDao.insertSettings(
            Settings(
                defaultCurrency = CurrencyCode.CZK,
                uiLanguage = LanguageCode.EN,
                themeMode = ThemeMode.DARK
            ).toEntity()
        )
    }
}