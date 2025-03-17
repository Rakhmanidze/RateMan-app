package com.example.rateman

import com.example.rateman.ui.theme.CurrencyCode

class Bank(private val name: String, private val baseCurrency: CurrencyCode, private val rates: List<CurrencyRate>) {
    fun get(): String {
        return name
    }

    fun getRate(currencyCode: CurrencyCode): CurrencyRate? {
        return rates.find { it.foreignCurrency == currencyCode }
    }
}

fun main() {
    val bank = Bank(
        name = "MyBank",
        baseCurrency = CurrencyCode.CZK,
        rates = listOf(
            CurrencyRate(CurrencyCode.USD, buyRate = 24.0, sellRate = 24.5),
            CurrencyRate(CurrencyCode.EUR, buyRate = 25.0, sellRate = 25.5),
        )
    )

    val usdRate = bank.getRate(CurrencyCode.USD)
    println("USD Buy: ${usdRate?.buyRate}, Sell: ${usdRate?.sellRate}")
}