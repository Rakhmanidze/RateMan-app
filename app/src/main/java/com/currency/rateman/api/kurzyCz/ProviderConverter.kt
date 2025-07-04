package com.currency.rateman.api.kurzyCz

import android.util.Log
import com.currency.rateman.api.kurzyCz.ProviderTypeClassifier
import com.currency.rateman.data.db.entity.CurrencyRateEntity
import com.currency.rateman.data.db.entity.ProviderEntity
import com.currency.rateman.data.model.CurrencyRate
import com.currency.rateman.data.model.Provider
import com.currency.rateman.data.model.enums.CurrencyCode
import com.currency.rateman.data.model.enums.ProviderType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object ProviderConverter {
    private val apiDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")

    fun toProviderEntity(api: ProviderAPI): ProviderEntity {
        return ProviderEntity(
            name = api.banka,
            baseCurrency = "CZK",
            phoneNumber = "",
            type = ProviderTypeClassifier.determineProviderType(api.banka).name
        )
    }

    fun toCurrencyRateEntities(apiProvider: ProviderAPI, providerId: Long): List<CurrencyRateEntity> {
        return apiProvider.kurzy.map { (currencyCode, currency) ->
            CurrencyRateEntity(
                providerId = providerId,
                foreignCurrency = currencyCode,
                buyRate = currency.dev_nakup ?: currency.val_nakup ?: 0.0,
                sellRate = currency.dev_prodej ?: currency.val_prodej ?: 0.0,
                date = apiProvider.den
            )
        }
    }

    fun toProvider(entity: ProviderEntity, rates: List<CurrencyRateEntity>): Provider {
        return Provider(
            id = entity.id.toLong(),
            name = entity.name,
            baseCurrency = CurrencyCode.valueOf(entity.baseCurrency),
            rates = rates.map { rate ->
                CurrencyRate(
                    foreignCurrency = CurrencyCode.valueOf(rate.foreignCurrency),
                    buyRate = rate.buyRate,
                    sellRate = rate.sellRate,
                    date = try {
                        LocalDate.parse(rate.date, apiDateFormatter)
                    } catch (_: Exception) {
                        try {
                            LocalDate.parse(rate.date, DateTimeFormatter.ISO_LOCAL_DATE)
                        } catch (e2: Exception) {
                            Log.e("Converter", "Failed to parse date $rate.date", e2)
                            LocalDate.now()
                        }
                    }
                )
            },
            phoneNumber = entity.phoneNumber,
            type = ProviderType.valueOf(entity.type)
        )
    }
}