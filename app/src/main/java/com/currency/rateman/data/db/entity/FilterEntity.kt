package com.currency.rateman.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.currency.rateman.data.model.enums.CurrencyCode
import com.currency.rateman.data.model.enums.ProviderType
import com.currency.rateman.data.model.enums.RateSortType

@Entity(tableName = "filters")
data class FilterEntity(
    @PrimaryKey
    val id: Long = 0,
    val selectedProviderType: String = ProviderType.ALL.name,
    val targetCurrency: String = CurrencyCode.EUR.name,
    val selectedRateSortType: String = RateSortType.BEST_RATE.name
)