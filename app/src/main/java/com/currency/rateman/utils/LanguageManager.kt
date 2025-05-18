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
            wrapContextWithLanguage(context, savedLanguage.toLanguageCode())
        }
    }

    fun setAppLanguage(context: Context, language: LanguageCode) {
        val languageCode = language.toLanguageCode()
        val appLocale = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(appLocale)

        val resources = context.resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(language.toLocale())
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    fun wrapContextWithLanguage(context: Context, languageCode: String): Context {
        val locale = languageCode.toLocale()
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }

    private fun LanguageCode.toLanguageCode(): String = when (this) {
        LanguageCode.EN -> "en"
        LanguageCode.CS -> "cs"
        LanguageCode.RU -> "ru"
        LanguageCode.ES -> "es"
        LanguageCode.UK -> "uk"
        LanguageCode.KY -> "ky"
        LanguageCode.TR -> "tr"
    }

    private fun LanguageCode.toLocale(): Locale = when (this) {
        LanguageCode.EN -> Locale.ENGLISH
        LanguageCode.CS -> Locale("cs")
        LanguageCode.RU -> Locale("ru")
        LanguageCode.ES -> Locale("es")
        LanguageCode.UK -> Locale("uk")
        LanguageCode.KY -> Locale("ky")
        LanguageCode.TR -> Locale("tr")
    }

    private fun String.toLocale(): Locale = when (this) {
        "cs" -> Locale("cs")
        "ru" -> Locale("ru")
        "es" -> Locale("es")
        "uk" -> Locale("uk")
        "ky" -> Locale("ky")
        "tr" -> Locale("tr")
        else -> Locale.ENGLISH
    }
}