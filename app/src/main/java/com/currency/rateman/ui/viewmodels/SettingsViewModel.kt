package com.currency.rateman.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.currency.rateman.data.model.Settings
import com.currency.rateman.data.model.CurrencyCode
import com.currency.rateman.data.model.LanguageCode
import com.currency.rateman.data.model.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel  : ViewModel() {
    private val _settings = MutableStateFlow(
        Settings(
            CurrencyCode.EUR,
            LanguageCode.EN,
            ThemeMode.LIGHT
        )
    )

    val settings: StateFlow<Settings> = _settings.asStateFlow()

    fun updateLanguage(language: LanguageCode) {
        _settings.value = _settings.value.copy(uiLanguage = language)
    }

    fun updateTheme(newTheme: ThemeMode) {
        _settings.value = _settings.value.copy(themeMode = newTheme)
    }

    fun updateCurrency(newCurrency: CurrencyCode) {
        _settings.value = _settings.value.copy(defaultCurrency = newCurrency)
    }
}