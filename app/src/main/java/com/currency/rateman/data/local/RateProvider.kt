package com.currency.rateman.data.local

data class RateProvider(
    val name: String,
    val baseCurrency: CurrencyCode,
    val rates: List<CurrencyRate>,
    val phoneNumber: String,
    val providerType: ProviderType,
)