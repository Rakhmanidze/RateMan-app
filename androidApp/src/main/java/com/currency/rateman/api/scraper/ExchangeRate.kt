package com.currency.rateman.api.scraper

data class ExchangeRate(
    val currency: String,
    val weBuy: String,
    val weSell: String
)
