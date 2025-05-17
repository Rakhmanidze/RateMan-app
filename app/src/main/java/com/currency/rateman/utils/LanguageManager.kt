package com.currency.rateman.utils

import android.content.Context
import com.currency.rateman.data.db.RateManDatabase
import com.currency.rateman.data.model.LanguageCode
import kotlinx.coroutines.flow.firstOrNull

object LanguageManager {
    suspend fun applySavedLanguage(context: Context): Boolean {
        val settingsDao = RateManDatabase.getDatabase(context).settingsDao()
        val settings = settingsDao.getSettings().firstOrNull()
        val savedLanguage = LanguageCode.valueOf(settings?.uiLanguage ?: LanguageCode.EN.name)

        val currentLanguage = context.resources.configuration.locales[0].language
        val desiredLanguageCode = when (savedLanguage) {
            LanguageCode.EN -> "en"
            LanguageCode.CS -> "cs"
            LanguageCode.RU -> "ru"
            LanguageCode.ES -> "es"
        }

        return if (currentLanguage != desiredLanguageCode) {
            LanguageHelper.setAppLanguage(context, savedLanguage)
            true
        } else {
            false
        }
    }
}