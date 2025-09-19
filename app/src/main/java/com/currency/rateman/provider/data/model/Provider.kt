package com.currency.rateman.provider.data.model

import com.currency.rateman.core.data.model.CurrencyRate
import com.currency.rateman.core.data.model.enums.CurrencyCode
import com.currency.rateman.provider.data.model.enums.ProviderType

data class Provider(
    val id: Long,
    val name: String,
    val baseCurrency: CurrencyCode,
    val rates: List<CurrencyRate>,
    val phoneNumber: String,
    val type: ProviderType
)