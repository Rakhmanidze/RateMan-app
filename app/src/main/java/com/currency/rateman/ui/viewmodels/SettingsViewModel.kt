package com.currency.rateman.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currency.rateman.data.model.Settings
import com.currency.rateman.data.model.CurrencyCode
import com.currency.rateman.data.model.LanguageCode
import com.currency.rateman.data.model.ThemeMode
import com.currency.rateman.data.repository.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val repository: SettingsRepository) : ViewModel() {
    val settings: StateFlow<Settings> = repository.getSettings()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Settings(
                defaultCurrency = CurrencyCode.CZK,
                uiLanguage = LanguageCode.EN,
                themeMode = ThemeMode.DARK
            )
        )

    fun updateLanguage(language: LanguageCode) {
        viewModelScope.launch {
            repository.saveSettings(settings.value.copy(uiLanguage = language))
        }
    }

    fun updateTheme(newTheme: ThemeMode) {
        viewModelScope.launch {
            repository.saveSettings(settings.value.copy(themeMode = newTheme))
        }
    }

    fun updateCurrency(newCurrency: CurrencyCode) {
        viewModelScope.launch {
            repository.saveSettings(settings.value.copy(defaultCurrency = newCurrency))
        }
    }

    fun resetSettings() {
        viewModelScope.launch {
            repository.resetSettings()
        }
    }
}