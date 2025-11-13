package com.currency.rateman.core.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.currency.rateman.core.domain.model.CurrencyCode
import com.currency.rateman.core.domain.model.RateSortType
import com.currency.rateman.provider.domain.model.ProviderType

@Entity(tableName = "filters")
data class FilterEntity(
    @PrimaryKey
    val id: Long = 0,
    val selectedProviderType: String = ProviderType.ALL.name,
    val targetCurrency: String = CurrencyCode.EUR.name,
    val selectedRateSortType: String = RateSortType.BEST_RATE.name
)