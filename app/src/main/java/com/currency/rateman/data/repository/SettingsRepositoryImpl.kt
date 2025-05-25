package com.currency.rateman.data.repository

import androidx.room.Transaction
import com.currency.rateman.data.db.dao.SettingsDao
import com.currency.rateman.data.db.entity.SettingsEntity
import com.currency.rateman.data.model.enums.CurrencyCode
import com.currency.rateman.data.model.enums.LanguageCode
import com.currency.rateman.data.model.Settings
import com.currency.rateman.data.model.enums.ThemeMode
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

    override suspend fun saveSettings(settings: Settings) {
        settingsDao.insertSettings(settings.toEntity().copy(id = 0))
    }

    override suspend fun editSettings(
        currencyCode: CurrencyCode?,
        languageCode: LanguageCode?,
        themeMode: ThemeMode?
    ) {
        ensureSettingsExist()
        val current = settingsDao.getSettings().first() ?: return
        settingsDao.updateSettings(current.copy(
            defaultCurrency = currencyCode?.name ?: current.defaultCurrency,
            uiLanguage = languageCode?.name ?: current.uiLanguage,
            themeMode = themeMode?.name ?: current.themeMode
        ))
    }


    @Transaction
    override suspend fun resetSettings() {
        settingsDao.deleteAllSettings()
        settingsDao.insertSettings(SettingsEntity(id = 0))
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
}