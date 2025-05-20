package com.currency.rateman.api

import com.currency.rateman.data.db.entity.CurrencyRateEntity
import com.currency.rateman.data.db.entity.RateProviderEntity
import com.currency.rateman.data.model.CurrencyCode
import com.currency.rateman.data.model.CurrencyRate
import com.currency.rateman.data.model.ProviderType
import com.currency.rateman.data.model.RateProvider
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object RateProviderConverter {
    fun toRateProviderEntity(api: RateProviderAPI): RateProviderEntity {
        return RateProviderEntity(
            name = api.banka,
            baseCurrency = "CZK",
            phoneNumber = "",
            type = ProviderTypeClassifier.determineProviderType(api.banka).name
        )
    }

    fun toCurrencyRateEntities(apiProvider: RateProviderAPI, providerId: Long): List<CurrencyRateEntity> {
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

    fun toRateProvider(entity: RateProviderEntity, rates: List<CurrencyRateEntity>): RateProvider {
        return RateProvider(
            id = entity.id.toInt(),
            name = entity.name,
            baseCurrency = CurrencyCode.valueOf(entity.baseCurrency),
            rates = rates.map { rate ->
                CurrencyRate(
                    foreignCurrency = CurrencyCode.valueOf(rate.foreignCurrency),
                    buyRate = rate.buyRate,
                    sellRate = rate.sellRate,
                    date = LocalDate.parse(rate.date, DateTimeFormatter.ISO_LOCAL_DATE)
                )
            },
            phoneNumber = entity.phoneNumber,
            type = ProviderType.valueOf(entity.type)
        )
    }
}