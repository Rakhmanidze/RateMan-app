package com.currency.rateman.data.repository

import com.currency.rateman.data.db.dao.SettingsDao
import com.currency.rateman.data.db.entity.SettingsEntity
import com.currency.rateman.data.model.CurrencyCode
import com.currency.rateman.data.model.LanguageCode
import com.currency.rateman.data.model.Settings
import com.currency.rateman.data.model.ThemeMode
import com.currency.rateman.data.model.toEntity
import com.currency.rateman.data.model.toSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first

class SettingsRepositoryImpl(private val settingsDao: SettingsDao) : SettingsRepository {
    override fun getSettings(): Flow<Settings> {
        return settingsDao.getSettings().map { entity ->
            entity?.toSettings() ?: getDefaultSettings()
        }
    }

    override suspend fun ensureSettingsExist() {
        if (settingsDao.getSettingsCount() == 0) {
            settingsDao.insertSettings(SettingsEntity(id = 0))
        }
    }

    private fun getDefaultSettings(): Settings {
        return Settings(
            defaultCurrency = CurrencyCode.DKK,
            uiLanguage = LanguageCode.EN,
            themeMode = ThemeMode.DARK
        )
    }

    override suspend fun editSettings(
        currencyCode: CurrencyCode?,
        languageCode: LanguageCode?,
        themeMode: ThemeMode?
    ) {
        ensureSettingsExist()
        val currentEntity = settingsDao.getSettings().first() ?: SettingsEntity(id = 0)
        val updatedEntity = currentEntity.copy(
            defaultCurrency = currencyCode?.name ?: currentEntity.defaultCurrency,
            uiLanguage = languageCode?.name ?: currentEntity.uiLanguage,
            themeMode = themeMode?.name ?: currentEntity.themeMode
        )
        settingsDao.updateSettings(updatedEntity)
    }

    override suspend fun saveSettings(settings: Settings) {
        settingsDao.insertSettings(settings.toEntity().copy(id = 0))
    }

    override suspend fun resetSettings() {
        settingsDao.deleteAllSettings()
        settingsDao.insertSettings(SettingsEntity(id = 0))
    }
}