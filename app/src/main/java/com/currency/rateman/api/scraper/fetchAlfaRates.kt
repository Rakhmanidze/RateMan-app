package com.currency.rateman.api.scraper

import com.currency.rateman.provider.utils.ProviderConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

suspend fun fetchAlfaPragueRates(): List<ExchangeRate> = withContext(Dispatchers.IO) {
    val url = ProviderConstants.Urls.ALFA_PRAGUE
    val result = mutableListOf<ExchangeRate>()

    try {
        val doc = Jsoup.connect(url).get()

        val table = doc.selectFirst("table")
        val rows = table?.select("tr") ?: emptyList()

        var startProcessing = false
        for (row in rows) {
            val cols = row.select("td")
            if (cols.size >= 9) {
                val currencyCode = cols[2].text().trim()
                if (currencyCode.isEmpty()) continue

                if (currencyCode == "USD") {
                    startProcessing = true
                }

                if (startProcessing) {
                    val standardBuy = cols[4].text().trim()
                    val standardSell = cols[5].text().trim()
                    result.add(ExchangeRate(currencyCode, standardBuy, standardSell))
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    result
}
