package com.currency.rateman.core.data.model

import com.currency.rateman.core.domain.model.CurrencyCode
import com.currency.rateman.provider.data.model.ProviderType
import com.currency.rateman.core.domain.model.RateSortType

data class Filter(
    val selectedProviderType: ProviderType,
    val targetCurrency: CurrencyCode,
    val selectedRateSortType: RateSortType
)