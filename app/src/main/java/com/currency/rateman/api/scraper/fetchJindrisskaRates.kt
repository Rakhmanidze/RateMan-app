package com.currency.rateman.api.scraper

import com.currency.rateman.provider.utils.ProviderConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

suspend fun fetchJindrisskaExchangeRates(): List<ExchangeRate> = withContext(Dispatchers.IO) {
    val url = ProviderConstants.Urls.JINDRISSKA_EXCHANGE
    val result = mutableListOf<ExchangeRate>()

    try {
        val doc = Jsoup.connect(url).get()

        val table = doc.selectFirst("table.kurzy") ?: return@withContext emptyList()
        val rows = table.select("tr")

        for (row in rows.drop(1)) {
            val cols = row.select("td")
            if (cols.size >= 6) {
                val rawCurrency = cols[1].text().trim()
                val currency = rawCurrency.replace(Regex("^\\d+\\s*"), "")
                val weBuy = cols[2].text().trim()
                val weSell = cols[3].text().trim()
                result.add(ExchangeRate(currency, weBuy, weSell))
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    result
}