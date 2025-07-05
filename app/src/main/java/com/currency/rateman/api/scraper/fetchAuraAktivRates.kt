package com.currency.rateman.api.scraper

import com.currency.rateman.data.model.enums.CurrencyCode
import com.currency.rateman.utils.ProviderConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

suspend fun fetchAuraAktivRates(): List<ExchangeRate> = withContext(Dispatchers.IO) {
    val url = ProviderConstants.Urls.AURA_AKTIV
    val result = mutableListOf<ExchangeRate>()

    try {
        val doc = Jsoup.connect(url)
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/137.0.0.0 Safari/537.36")
            .get()
        val tbody = doc.selectFirst("tbody") ?: return@withContext emptyList()
        val rows = tbody.select("tr")

        for (row in rows) {
            val cols = row.select("td")
            if (cols.size >= 8) {
                val currency = cols[2].text().trim()
                val weBuy = cols[6].text().trim().replace(",", ".")
                val weSell = cols[7].text().trim().replace(",", ".")

                if (currency.isNotEmpty() && weBuy.isNotEmpty() && weSell.isNotEmpty()) {
                    try {
                        CurrencyCode.valueOf(currency)
                        result.add(ExchangeRate(currency, weBuy, weSell))
                    } catch (_: IllegalArgumentException) {
                        continue
                    }
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    result
}