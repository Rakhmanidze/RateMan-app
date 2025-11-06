package com.currency.rateman.provider.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "rate_providers", indices = [Index(value = ["name"], unique = true)])
data class ProviderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String = "",
    val baseCurrency: String = "",
    val phoneNumber: String = "",
    val type: String = ""
)