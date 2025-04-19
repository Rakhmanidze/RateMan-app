package com.currency.rateman.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_rates")
data class CurrencyRateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val foreignCurrency: String,
    val buyRate: Double,
    val sellRate: Double
)
