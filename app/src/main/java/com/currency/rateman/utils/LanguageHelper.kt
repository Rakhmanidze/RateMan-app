package com.currency.rateman.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

object LanguageHelper {

    fun setAppLanguage(context: Context, languageCode: String) {
        val appLocale = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(appLocale)

        if (context is Activity) {
            context.recreate()
        }
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