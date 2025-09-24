package com.currency.rateman.core.data.model

import com.currency.rateman.core.domain.model.CurrencyCode
import com.currency.rateman.core.domain.app.LanguageCode
import com.currency.rateman.core.domain.app.ThemeMode

data class Settings(
    val baseCurrency: CurrencyCode,
    val uiLanguage: LanguageCode,
    val themeMode: ThemeMode
)