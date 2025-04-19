package com.currency.rateman.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rate_providers")
data class RateProviderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val baseCurrency: String,
    val ratesDate: String,
    val phoneNumber: String,
    val type: String
)
