package com.currency.rateman.data.model

import com.currency.rateman.data.db.entity.SettingsEntity

fun Settings.toEntity() : SettingsEntity {
    return SettingsEntity(
        id = 0,
        defaultCurrency = defaultCurrency.name,
        uiLanguage = uiLanguage.name,
        themeMode = themeMode.name
    )
}

fun Settings.toSettings() : Settings {
    return Settings(
        defaultCurrency = CurrencyCode.valueOf(defaultCurrency.toString()),
        uiLanguage = LanguageCode.valueOf(uiLanguage.toString()),
        themeMode = ThemeMode.valueOf(themeMode.toString())
    )
}