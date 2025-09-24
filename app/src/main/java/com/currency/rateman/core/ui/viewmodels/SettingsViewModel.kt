package com.currency.rateman.core.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currency.rateman.core.domain.model.Settings
import com.currency.rateman.core.domain.model.CurrencyCode
import com.currency.rateman.core.domain.app.LanguageCode
import com.currency.rateman.core.domain.app.ThemeMode
import com.currency.rateman.core.data.repository.SettingsRepository
import com.currency.rateman.core.utils.LanguageManager
import com.currency.rateman.core.utils.ThemeManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(private val repository: SettingsRepository) : ViewModel() {
    private val _settings = MutableStateFlow<Settings?>(null)
    val settings: StateFlow<Settings?> = _settings.asStateFlow()

    init {
        viewModelScope.launch {
            repository.ensureSettingsExist()
            repository.getSettings().collect { settings ->
                _settings.update { settings }
            }
        }
    }

    fun updateLanguage(context: Context, language: LanguageCode) {
        viewModelScope.launch {
            repository.editSettings(languageCode = language)
            LanguageManager.setAppLanguage(context, language)
        }
    }

    fun updateTheme(newTheme: ThemeMode) {
        viewModelScope.launch {
            repository.editSettings(themeMode = newTheme)
            ThemeManager.setAppTheme(newTheme)
        }
    }

    fun updateCurrency(newCurrency: CurrencyCode) {
        viewModelScope.launch {
            repository.editSettings(currencyCode = newCurrency)
        }
    }
}