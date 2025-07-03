package com.currency.rateman.api.scraper

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

suspend fun fetchAlfaPragueRates(): List<ExchangeRate> = withContext(Dispatchers.IO) {
    val url = "https://www.alfaprague.cz/web2/"
    val result = mutableListOf<ExchangeRate>()

    try {
        val doc = Jsoup.connect(url)
            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
            .header("Referer", "https://www.alfaprague.cz/")
            .get()

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
