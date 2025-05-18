package com.currency.rateman.utils

import android.content.Context
import com.currency.rateman.data.db.RateManDatabase
import com.currency.rateman.data.model.LanguageCode
import com.currency.rateman.data.repository.SettingsRepository
import com.currency.rateman.data.repository.SettingsRepositoryImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object LanguageInitializer {
    private fun getSettingsRepository(context: Context): SettingsRepository {
        return SettingsRepositoryImpl(RateManDatabase.getDatabase(context).settingsDao())
    }

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
        return getSettingsRepository(context).getSettings().first().uiLanguage
    }
}
// todo too many files reduce 2 to one or doesnt make sense