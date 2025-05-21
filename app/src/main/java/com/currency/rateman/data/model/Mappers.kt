package com.currency.rateman.data.model

import com.currency.rateman.data.db.entity.FilterEntity
import com.currency.rateman.data.db.entity.SettingsEntity
import com.currency.rateman.data.model.enums.CurrencyCode
import com.currency.rateman.data.model.enums.LanguageCode
import com.currency.rateman.data.model.enums.ProviderType
import com.currency.rateman.data.model.enums.RateSortType
import com.currency.rateman.data.model.enums.ThemeMode

fun Settings.toEntity() : SettingsEntity {
    return SettingsEntity(
        id = 0,
        defaultCurrency = defaultCurrency.name,
        uiLanguage = uiLanguage.name,
        themeMode = themeMode.name
    )
}

fun SettingsEntity.toSettings() : Settings {
    return Settings(
        defaultCurrency = CurrencyCode.valueOf(defaultCurrency),
        uiLanguage = LanguageCode.valueOf(uiLanguage),
        themeMode = ThemeMode.valueOf(themeMode)
    )
}

fun Filter.toEntity() : FilterEntity {
    return FilterEntity(
        id = 0,
        selectedProviderType = selectedProviderType.name,
        selectedCurrency = selectedCurrency.name,
        selectedRateSortType = selectedRateSortType.name
    )
}

fun FilterEntity.toFilter() : Filter {
    return Filter(
        selectedProviderType = ProviderType.valueOf(selectedProviderType),
        selectedCurrency = CurrencyCode.valueOf(selectedCurrency),
        selectedRateSortType = RateSortType.valueOf(selectedRateSortType)
    )
}