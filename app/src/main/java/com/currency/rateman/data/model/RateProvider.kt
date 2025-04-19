package com.currency.rateman.data.model

data class RateProvider(
    val name: String,
    val baseCurrency: CurrencyCode,
    val rates: List<CurrencyRate>,
    val nearestBranchAddress: Address,
    val phoneNumber: String,
)