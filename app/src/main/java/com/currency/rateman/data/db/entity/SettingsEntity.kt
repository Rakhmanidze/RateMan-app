package com.currency.rateman.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class SettingsEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val defaultCurrency: String = "",
    val uiLanguage: String = "",
    val themeMode: String = "",
)