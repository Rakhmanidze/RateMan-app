package com.currency.rateman.api.scraper

import com.currency.rateman.utils.ProviderConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

suspend fun fetchExchange8Rates(): List<ExchangeRate> = withContext(Dispatchers.IO) {
    val url = ProviderConstants.Urls.EXCHANGE8
    val result = mutableListOf<ExchangeRate>()

    try {
        val doc = Jsoup.connect(url).get()
        val table = doc.selectFirst("div.exchange-list > table") ?: return@withContext emptyList()
        val rows = table.select("tr.exchange-list-item")

        for (row in rows) {
            val cols = row.select("td")
            if (cols.size >= 5) {
                val currency = cols[1].selectFirst("span")?.text()?.trim() ?: continue
                val weBuy = cols[3].text().trim().replace(",", ".")
                val weSell = cols[4].text().trim().replace(",", ".")

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