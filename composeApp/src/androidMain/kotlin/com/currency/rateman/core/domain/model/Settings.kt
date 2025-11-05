package com.currency.rateman.core.domain.model

import com.currency.rateman.core.domain.app.LanguageCode
import com.currency.rateman.core.domain.app.ThemeMode
import com.currency.shared.core.domain.model.CurrencyCode

data class Settings(
    val baseCurrency: CurrencyCode,
    val uiLanguage: LanguageCode,
    val themeMode: ThemeMode
)