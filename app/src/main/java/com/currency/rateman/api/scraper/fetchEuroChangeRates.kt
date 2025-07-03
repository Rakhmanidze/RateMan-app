package com.currency.rateman.api.scraper

import com.currency.rateman.utils.ProviderConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

suspend fun fetchEuroChangeRates(): List<ExchangeRate> = withContext(Dispatchers.IO) {
    val url = ProviderConstants.Urls.EURO_CHANGE
    val result = mutableListOf<ExchangeRate>()

    try {
        val doc: Document = Jsoup.connect(url).get()
        val table = doc.selectFirst("table.exchangetbl") ?: return@withContext emptyList()
        val rows = table.select("tr")

        for (row in rows.drop(1)) { // Skip header row
            val cols = row.select("td")
            if (cols.size >= 7) {
                val currency = cols[1].text().trim()
                val weBuy = cols[3].text().trim() // Buy rate
                val weSell = cols[4].text().trim() // Sell rate
                result.add(ExchangeRate(currency, weBuy, weSell))
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    result
}