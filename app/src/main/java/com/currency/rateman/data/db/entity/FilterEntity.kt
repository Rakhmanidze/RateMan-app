package com.currency.rateman.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.currency.rateman.data.model.CurrencyCode
import com.currency.rateman.data.model.ProviderType
import com.currency.rateman.data.model.RateSortType

@Entity(tableName = "filters")
data class FilterEntity(
    @PrimaryKey
    val id: Long = 0,
    val selectedProviderType: String = ProviderType.ALL.name,
    val selectedCurrency: String = CurrencyCode.EUR.name,
    val selectedRateSortType: String = RateSortType.BEST_RATE.name
)