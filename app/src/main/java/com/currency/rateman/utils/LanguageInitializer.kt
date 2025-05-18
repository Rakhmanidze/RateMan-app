package com.currency.rateman.utils

import android.content.Context
import com.currency.rateman.data.db.RateManDatabase
import com.currency.rateman.data.model.LanguageCode
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

object LanguageInitializer {
    fun initLanguage(context: Context) {
        runBlocking {
            val savedLanguage = getSavedLanguage(context)
            LanguageHelper.setAppLanguage(context, savedLanguage)
        }
    }

    fun wrapContextWithSavedLanguage(context: Context): Context {
        return runBlocking {
            val savedLanguage = getSavedLanguage(context)
            val languageCode = when (savedLanguage) {
                LanguageCode.EN -> "en"
                LanguageCode.CS -> "cs"
                LanguageCode.RU -> "ru"
                LanguageCode.ES -> "es"
                LanguageCode.UK -> "uk"
                LanguageCode.KY -> "ky"
                LanguageCode.TR -> "tr"
            }
            LanguageHelper.wrapContextWithLanguage(context, languageCode)
        }
    }

    private suspend fun getSavedLanguage(context: Context): LanguageCode {
        val settingsDao = RateManDatabase.getDatabase(context).settingsDao()
        val settings = settingsDao.getSettings().firstOrNull()
        return settings?.uiLanguage?.let { LanguageCode.valueOf(it) } ?: LanguageCode.EN
    }
} //todo this func shouldnt use repo not dao ? //todo too many files reduce 3