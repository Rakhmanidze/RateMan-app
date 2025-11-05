package com.currency.rateman.core.data.mapper

import com.currency.rateman.core.data.entity.FilterEntity
import com.currency.rateman.core.data.entity.SettingsEntity
import com.currency.rateman.core.domain.model.Filter
import com.currency.rateman.core.domain.model.Settings
import com.currency.shared.core.domain.model.CurrencyCode
import com.currency.rateman.core.domain.app.LanguageCode
import com.currency.rateman.provider.data.model.ProviderType
import com.currency.rateman.core.domain.model.RateSortType
import com.currency.rateman.core.domain.app.ThemeMode

fun Settings.toEntity() : SettingsEntity {
    return SettingsEntity(
        id = 0,
        baseCurrency = baseCurrency.name,
        uiLanguage = uiLanguage.name,
        themeMode = themeMode.name
    )
}

fun SettingsEntity.toSettings() : Settings {
    return Settings(
        baseCurrency = CurrencyCode.valueOf(baseCurrency),
        uiLanguage = LanguageCode.valueOf(uiLanguage),
        themeMode = ThemeMode.valueOf(themeMode)
    )
}

fun Filter.toEntity() : FilterEntity {
    return FilterEntity(
        id = 0,
        selectedProviderType = selectedProviderType.name,
        targetCurrency = targetCurrency.name,
        selectedRateSortType = selectedRateSortType.name
    )
}

fun FilterEntity.toFilter() : Filter {
    return Filter(
        selectedProviderType = ProviderType.valueOf(selectedProviderType),
        targetCurrency = CurrencyCode.valueOf(targetCurrency),
        selectedRateSortType = RateSortType.valueOf(selectedRateSortType)
    )
}