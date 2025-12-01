package com.currency.rateman.core.domain.model

import com.currency.rateman.core.domain.app.LanguageCode
import com.currency.rateman.core.domain.app.ThemeMode

data class Settings(
    val baseCurrency: CurrencyCode,
    val uiLanguage: LanguageCode,
)