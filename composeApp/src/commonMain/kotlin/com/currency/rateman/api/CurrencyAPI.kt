package com.currency.rateman.api

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyAPI(
    val jednotka: Int,
    val dev_nakup: Double?,
    val dev_prodej: Double?,
    val val_nakup: Double?,
    val val_prodej: Double?,
    val nazev: String,
    val url: String
)