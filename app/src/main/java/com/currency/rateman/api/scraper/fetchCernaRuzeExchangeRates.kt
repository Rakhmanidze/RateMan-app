package com.currency.rateman.api.scraper

import com.currency.rateman.utils.ProviderConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

suspend fun fetchCernaRuzeExchangeRates(): List<ExchangeRate> = withContext(Dispatchers.IO) {
    val url = ProviderConstants.Urls.CERNA_RUZE_EXCHANGE
    val result = mutableListOf<ExchangeRate>()

    try {
        val doc = Jsoup.connect(url).get()
        val rows = doc.select("div.row-margin-top")

        for (row in rows) {
            val currencyBlock = row.selectFirst("p.p-bloc-3-style")?.text()?.trim() ?: continue
            val currency = currencyBlock.split(" ").lastOrNull()?.trim() ?: continue
            if (currency.isEmpty()) continue

            val rateBlocks = row.select("div.col-md-3").takeLast(2)
            if (rateBlocks.size < 2) continue

            val buyRates = rateBlocks[0].select("p.p-5-style")
            val sellRates = rateBlocks[1].select("p.p-5-style")

            val weBuy = buyRates.getOrNull(0)?.text()?.trim()?.replace(",", ".") ?: continue
            val weSell = buyRates.getOrNull(1)?.text()?.trim()?.replace(",", ".") ?: continue

            if (weBuy.isNotEmpty() && weSell.isNotEmpty()) {
                result.add(ExchangeRate(currency, weBuy, weSell))
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    result
}