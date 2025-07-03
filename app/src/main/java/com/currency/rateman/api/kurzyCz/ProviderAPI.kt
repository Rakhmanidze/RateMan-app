package com.currency.rateman.api.kurzyCz

import kotlinx.serialization.Serializable

@Serializable
data class ProviderAPI(
    val den: String,
    val denc: String,
    val banka: String,
    val url: String,
    val kurzy: Map<String, CurrencyAPI>
)