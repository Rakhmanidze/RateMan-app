package com.currency.rateman.core.domain.model

import com.currency.rateman.provider.data.model.ProviderType
import com.currency.shared.core.domain.model.CurrencyCode

data class Filter(
    val selectedProviderType: ProviderType,
    val targetCurrency: CurrencyCode,
    val selectedRateSortType: RateSortType
)