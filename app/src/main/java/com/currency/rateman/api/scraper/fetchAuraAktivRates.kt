package com.currency.rateman.api.scraper

import com.currency.rateman.core.data.model.enums.CurrencyCode
import com.currency.rateman.provider.utils.ProviderConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

suspend fun fetchAuraAktivRates(): List<ExchangeRate> = withContext(Dispatchers.IO) {
    val url = ProviderConstants.Urls.AURA_AKTIV
    val result = mutableListOf<ExchangeRate>()

    try {
        val doc = Jsoup.connect(url).get()
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