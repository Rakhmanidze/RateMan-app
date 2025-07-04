package com.currency.rateman.data.repository

import com.currency.rateman.data.db.dao.SettingsDao
import com.currency.rateman.data.db.entity.SettingsEntity
import com.currency.rateman.data.model.enums.CurrencyCode
import com.currency.rateman.data.model.enums.LanguageCode
import com.currency.rateman.data.model.Settings
import com.currency.rateman.data.model.enums.ThemeMode
import com.currency.rateman.data.mappers.toSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first

class SettingsRepositoryImpl(private val settingsDao: SettingsDao) : SettingsRepository {
    override fun getSettings(): Flow<Settings> {
        return settingsDao.getSettings().map { entity ->
            entity?.toSettings() ?: getDefaultSettings()
        }
    }

    override suspend fun editSettings(
        currencyCode: CurrencyCode?,
        languageCode: LanguageCode?,
        themeMode: ThemeMode?
    ) {
        ensureSettingsExist()
        val current = settingsDao.getSettings().first() ?: return
        settingsDao.updateSettings(current.copy(
            baseCurrency = currencyCode?.name ?: current.baseCurrency,
            uiLanguage = languageCode?.name ?: current.uiLanguage,
            themeMode = themeMode?.name ?: current.themeMode
        ))
    }

    override suspend fun ensureSettingsExist() {
        if (settingsDao.getSettingsCount() == 0) {
            settingsDao.insertSettings(SettingsEntity(id = 0))
        }
    }

    private fun getDefaultSettings(): Settings {
        return Settings(
            baseCurrency = CurrencyCode.DKK,
            uiLanguage = LanguageCode.EN,
            themeMode = ThemeMode.DARK
        )
    }
}