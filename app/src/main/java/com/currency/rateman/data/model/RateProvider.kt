package com.currency.rateman.data.model

data class RateProvider(
    val id: Int,
    val name: String,
    val baseCurrency: CurrencyCode,
    val rates: List<CurrencyRate>,
    val phoneNumber: String,
    val type: ProviderType
)