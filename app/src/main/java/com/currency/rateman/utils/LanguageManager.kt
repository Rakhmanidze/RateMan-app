package com.currency.rateman

import android.content.Context
import com.currency.rateman.data.db.RateManDatabase
import com.currency.rateman.data.model.LanguageCode
import kotlinx.coroutines.flow.firstOrNull

object LanguageManager {
    suspend fun applySavedLanguage(context: Context): Boolean {
        val settingsDao = RateManDatabase.getDatabase(context).settingsDao()
        val settings = settingsDao.getSettings().firstOrNull()
        val savedLanguageCode = settings?.uiLanguage ?: LanguageCode.EN.name

        val currentLanguage = context.resources.configuration.locales[0].language
        val desiredLanguage = when (savedLanguageCode) {
            LanguageCode.EN.name -> "en"
            LanguageCode.CZ.name -> "cs"
            else -> "en"
        }

        return if (currentLanguage != desiredLanguage) {
            LanguageHelper.setAppLanguage(context, desiredLanguage)
            true
        } else {
            false
        }
    }
}