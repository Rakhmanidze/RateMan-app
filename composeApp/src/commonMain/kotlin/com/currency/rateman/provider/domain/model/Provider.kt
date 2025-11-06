package com.currency.rateman.provider.domain.model

import com.currency.rateman.core.domain.model.CurrencyCode
import com.currency.rateman.core.domain.model.CurrencyRate
import com.currency.rateman.provider.domain.model.ProviderType

data class Provider(
    val id: Long,
    val name: String,
    val baseCurrency: CurrencyCode,
    val rates: List<CurrencyRate>,
    val phoneNumber: String,
    val type: ProviderType
)