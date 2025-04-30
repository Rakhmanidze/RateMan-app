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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsRepositoryImpl(private val settingsDao: SettingsDao) : SettingsRepository{
    init {
        CoroutineScope(Dispatchers.IO).launch {
            if (settingsDao.getSettingsCount() == 0) {
                settingsDao.insertSettings(SettingsEntity())
            }
        }
    }

    override fun getSettings(): Flow<Settings> {
        return settingsDao.getSettings().map { entity ->
            entity?.toSettings() ?: SettingsEntity(id = 1).toSettings()
        }
    }

    override suspend fun editSettings(
        currencyCode: CurrencyCode?,
        languageCode: LanguageCode?,
        themeMode: ThemeMode?
    ) {
        val currentSettings = getSettings().first()
        val updatedSettings = currentSettings.copy(
            defaultCurrency = (currencyCode ?: currentSettings.defaultCurrency),
            uiLanguage = (languageCode ?: currentSettings.uiLanguage),
            themeMode = (themeMode ?: currentSettings.themeMode)
        )
        settingsDao.updateSettings(updatedSettings.toEntity())
    }

    override suspend fun saveSettings(settings: Settings) {
        settingsDao.insertSettings(settings.toEntity())
    }

    override suspend fun resetSettings() {
        settingsDao.deleteAllSettings()

        settingsDao.insertSettings(
            Settings(
                defaultCurrency = CurrencyCode.DKK,
                uiLanguage = LanguageCode.EN,
                themeMode = ThemeMode.DARK
            ).toEntity()
        )
    }
}