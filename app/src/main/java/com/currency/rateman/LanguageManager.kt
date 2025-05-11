package com.currency.rateman

import android.content.Context
import com.currency.rateman.data.db.RateManDatabase
import com.currency.rateman.data.model.LanguageCode
import kotlinx.coroutines.flow.first

object LanguageManager {
    suspend fun applySavedLanguage(context: Context) {
        val settingsDao = RateManDatabase.getDatabase(context).settingsDao()
        val settings = settingsDao.getSettings().first()
        val languageTag = settings?.uiLanguage?.toLanguageTag() ?: LanguageCode.EN
        LanguageHelper.setAppLanguage(context, languageTag.toString())
    }

    private fun String.toLanguageTag(): String {
        return when (this) {
            LanguageCode.EN.name -> "en"
            LanguageCode.CZ.name -> "cs"
            else -> "en" // Default to English
        }
    }
}