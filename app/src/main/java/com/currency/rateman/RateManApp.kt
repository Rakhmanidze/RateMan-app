package com.currency.rateman

import android.app.Application
import android.content.res.Configuration
import com.currency.rateman.data.model.LanguageCode
import com.currency.rateman.data.repository.SettingsRepository
import com.currency.rateman.di.appModule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import java.util.*

class RateManApp: Application(), KoinComponent {
    private val settingsRepository: SettingsRepository by inject()

    override fun onCreate() {
        super.onCreate()
        AppContainer.init(applicationContext)
        startKoin {
            androidContext(this@RateManApp)
            modules(appModule)
        }
        updateAppLocale()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        updateAppLocale()
    }

    private fun updateAppLocale() {
        val settings = runBlocking { settingsRepository.getSettings().first() }
        val languageCode = settings.uiLanguage

        val locale = when (languageCode) {
            LanguageCode.SYSTEM -> Locale.getDefault()
            LanguageCode.EN -> Locale("en")
            LanguageCode.CZ -> Locale("cs")
        }

        Locale.setDefault(locale)
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}