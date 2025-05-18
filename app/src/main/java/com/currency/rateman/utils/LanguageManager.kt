package com.currency.rateman.utils

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.currency.rateman.data.db.RateManDatabase
import com.currency.rateman.data.model.LanguageCode
import com.currency.rateman.data.repository.SettingsRepository
import com.currency.rateman.data.repository.SettingsRepositoryImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.*

object LanguageManager {
    private fun getSettingsRepository(context: Context): SettingsRepository {
        return SettingsRepositoryImpl(RateManDatabase.getDatabase(context).settingsDao())
    }

    private suspend fun getSavedLanguage(context: Context): LanguageCode {
        return getSettingsRepository(context).getSettings().first().uiLanguage
    }

    fun initLanguage(context: Context) {
        runBlocking {
            val savedLanguage = getSavedLanguage(context)
            setAppLanguage(context, savedLanguage)
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
            wrapContextWithLanguage(context, languageCode)
        }
    }

    fun setAppLanguage(context: Context, language: LanguageCode) {
        val languageCode = when (language) {
            LanguageCode.EN -> "en"
            LanguageCode.CS -> "cs"
            LanguageCode.RU -> "ru"
            LanguageCode.ES -> "es"
            LanguageCode.UK -> "uk"
            LanguageCode.KY -> "ky"
            LanguageCode.TR -> "tr"
        }
        val appLocale = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(appLocale)

        val resources = context.resources
        val configuration = Configuration(resources.configuration)
        val locale = when (language) {
            LanguageCode.EN -> Locale.ENGLISH
            LanguageCode.CS -> Locale("cs")
            LanguageCode.RU -> Locale("ru")
            LanguageCode.ES -> Locale("es")
            LanguageCode.UK -> Locale("uk")
            LanguageCode.KY -> Locale("ky")
            LanguageCode.TR -> Locale("tr")
        }
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    fun wrapContextWithLanguage(context: Context, languageCode: String): Context {
        val locale = when (languageCode) {
            "cs" -> Locale("cs")
            "ru" -> Locale("ru")
            "es" -> Locale("es")
            "uk" -> Locale("uk")
            "ky" -> Locale("ky")
            "tr" -> Locale("tr")
            else -> Locale.ENGLISH
        }
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }
}