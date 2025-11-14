package com.currency.rateman.core.utils

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.currency.rateman.core.data.db.RateManDatabase
import com.currency.rateman.core.data.repository.SettingsRepositoryImpl
import com.currency.rateman.core.domain.app.LanguageCode
import com.currency.rateman.core.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Locale

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
            wrapContextWithLanguage(context, savedLanguage)
        }
    }

    fun setAppLanguage(context: Context, language: LanguageCode) {
        val appLocale = LocaleListCompat.forLanguageTags(language.toLanguageTag())
        AppCompatDelegate.setApplicationLocales(appLocale)

        val resources = context.resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(language.toLocale())
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    fun wrapContextWithLanguage(context: Context, language: LanguageCode): Context {
        val locale = language.toLocale()
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }

    private fun LanguageCode.toLanguageTag(): String = name.lowercase()

    private fun LanguageCode.toLocale(): Locale = Locale(name.lowercase())
}