package com.currency.rateman.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.currency.rateman.data.model.Profile
import com.currency.rateman.data.model.CurrencyCode
import com.currency.rateman.data.model.LanguageCode
import com.currency.rateman.data.model.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel  : ViewModel() {
    private val _profile = MutableStateFlow(
        Profile(
            CurrencyCode.EUR,
            LanguageCode.EN,
            ThemeMode.LIGHT
        )
    )

    val profile: StateFlow<Profile> = _profile.asStateFlow()

    fun updateLanguage(language: LanguageCode) {
        _profile.value = _profile.value.copy(uiLanguage = language)
    }

    fun updateTheme(newTheme: ThemeMode) {
        _profile.value = _profile.value.copy(themeMode = newTheme)
    }

    fun updateCurrency(newCurrency: CurrencyCode) {
        _profile.value = _profile.value.copy(defaultCurrency = newCurrency)
    }
}