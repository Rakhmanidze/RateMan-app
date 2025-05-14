package com.currency.rateman.utils

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.currency.rateman.data.model.LanguageCode
import java.util.*

object LanguageHelper {
    fun setAppLanguage(context: Context, language: LanguageCode) {
        val languageCode = when (language) {
            LanguageCode.EN -> "en"
            LanguageCode.CS -> "cs"
        }
        val appLocale = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(appLocale)

        val resources = context.resources
        val configuration = Configuration(resources.configuration)
        val locale = when (language) {
            LanguageCode.EN -> Locale.ENGLISH
            LanguageCode.CS -> Locale("cs")
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