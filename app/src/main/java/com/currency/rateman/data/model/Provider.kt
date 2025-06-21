package com.currency.rateman.data.model

import com.currency.rateman.data.model.enums.CurrencyCode
import com.currency.rateman.data.model.enums.ProviderType

data class Provider(
    val id: Long,
    val name: String,
    val baseCurrency: CurrencyCode,
    val rates: List<CurrencyRate>,
    val phoneNumber: String,
    val type: ProviderType
)