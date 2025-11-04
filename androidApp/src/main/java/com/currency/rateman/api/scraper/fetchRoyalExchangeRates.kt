package com.currency.rateman.api.scraper

import com.currency.rateman.provider.utils.ProviderConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

suspend fun fetchRoyalExchangeRates(): List<ExchangeRate> = withContext(Dispatchers.IO) {
    val url = ProviderConstants.Urls.ROYAL_EXCHANGE
    val result = mutableListOf<ExchangeRate>()

    try {
        val doc = Jsoup.connect(url).get()
        val table = doc.selectFirst("tbody") ?: return@withContext emptyList()
        val rows = table.select("tr")

        for (row in rows) {
            val cols = row.select("td")
            if (cols.size >= 9) {
                val currency = cols[2].text().trim()
                val weBuy = cols[7].text().trim()
                val weSell = cols[8].text().trim()

                if (currency.isNotEmpty() && weBuy.isNotEmpty() && weSell.isNotEmpty()) {
                    result.add(ExchangeRate(currency, weBuy, weSell))
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    result
}