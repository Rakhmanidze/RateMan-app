package com.currency.rateman.api

import kotlinx.serialization.Serializable

@Serializable
data class RateProviderAPI(
    val den: String,
    val denc: String,
    val banka: String,
    val url: String,
    val kurzy: Map<String, CurrencyAPI>
)