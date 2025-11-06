package com.currency.rateman.api.kurzyCz

import com.currency.rateman.core.data.entity.CurrencyRateEntity
import com.currency.rateman.provider.data.model.ProviderEntity
import com.currency.rateman.core.domain.model.CurrencyRate
import com.currency.rateman.provider.domain.model.Provider
import com.currency.rateman.core.domain.model.CurrencyCode
import com.currency.rateman.provider.data.model.ProviderType
import kotlinx.datetime.LocalDate

object ProviderConverter {
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
            id = entity.id,
            name = entity.name,
            baseCurrency = CurrencyCode.valueOf(entity.baseCurrency),
            rates = rates.map { rate ->
                CurrencyRate(
                    foreignCurrency = CurrencyCode.valueOf(rate.foreignCurrency),
                    buyRate = rate.buyRate,
                    sellRate = rate.sellRate,
                    date = try {
                        LocalDate.parse(rate.date)
                    } catch (_: Exception) {
                        try {
                            val parts = rate.date.split(".", "-", "/")
                            if (parts.size == 3) {
                                val day = parts[0].toInt()
                                val month = parts[1].toInt()
                                val year = parts[2].toInt()
                                LocalDate(year, month, day)
                            } else {
                                LocalDate(2000, 1, 1)
                            }
                        } catch (e2: Exception) {
                            println("Failed to parse date: ${rate.date} -> ${e2.message}")
                            LocalDate(2000, 1, 1)
                        }
                    }
                )
            },
            phoneNumber = entity.phoneNumber,
            type = ProviderType.valueOf(entity.type)
        )
    }
}