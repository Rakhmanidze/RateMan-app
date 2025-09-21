package com.currency.rateman.core.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.currency.rateman.provider.data.model.ProviderEntity

@Entity(
    tableName = "currency_rates",
    foreignKeys = [ForeignKey(
        entity = ProviderEntity::class,
        parentColumns = ["id"],
        childColumns = ["providerId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class CurrencyRateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val providerId: Long,
    val foreignCurrency: String = "",
    val buyRate: Double = 0.0,
    val sellRate: Double = 0.0,
    val date: String = ""
)