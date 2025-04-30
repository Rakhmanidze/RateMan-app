package com.currency.rateman.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class SettingsEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val defaultCurrency: String = "DKK",
    val uiLanguage: String = "EN",
    val themeMode: String = "DARK",
)