package com.currency.rateman.core.data.mapper

import com.currency.rateman.core.data.entity.FilterEntity
import com.currency.rateman.core.data.entity.SettingsEntity
import com.currency.rateman.core.domain.model.CurrencyCode
import com.currency.rateman.core.domain.model.Filter
import com.currency.rateman.core.domain.model.RateSortType
import com.currency.rateman.core.domain.model.Settings
import com.currency.rateman.provider.domain.model.ProviderType

fun Settings.toEntity() : SettingsEntity {
    return SettingsEntity(
        id = 0,
        baseCurrency = baseCurrency.name
    )
}

fun SettingsEntity.toSettings() : Settings {
    return Settings(
        baseCurrency = CurrencyCode.valueOf(baseCurrency)
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