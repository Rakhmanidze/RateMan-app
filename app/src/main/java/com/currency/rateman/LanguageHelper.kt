package com.currency.rateman

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.currency.rateman.data.db.RateManDatabase
import com.currency.rateman.data.model.LanguageCode
import kotlinx.coroutines.flow.firstOrNull
import java.util.*

object LanguageHelper {

    suspend fun applySavedLanguage(context: Context): Boolean {
        val settingsDao = RateManDatabase.getDatabase(context).settingsDao()
        val settings = settingsDao.getSettings().firstOrNull()
        val savedLanguageCode = settings?.uiLanguage ?: LanguageCode.EN

        val currentLanguage = context.resources.configuration.locales[0].language
        val desiredLanguage = when (savedLanguageCode) {
            LanguageCode.EN -> "en"
            LanguageCode.CZ -> "cs"
            else -> "en"
        }

        return if (currentLanguage != desiredLanguage) {
            setAppLanguage(context, desiredLanguage)
            true
        } else {
            false
        }
    }

    fun setAppLanguage(context: Context, languageCode: String) {
        val appLocale = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(appLocale)

        val resources = context.resources
        val configuration = Configuration(resources.configuration)
        val locale = when (languageCode) {
            "cs" -> Locale("cs")
            else -> Locale.ENGLISH
        }
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    fun wrapContextWithLanguage(context: Context, languageCode: String): Context {
        val locale = when (languageCode) {
            "cs" -> Locale("cs")
            else -> Locale.ENGLISH
        }
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }
}