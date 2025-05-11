package com.currency.rateman.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.currency.rateman.data.model.CurrencyCode
import com.currency.rateman.data.model.LanguageCode
import com.currency.rateman.data.model.ThemeMode

@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey
    val id: Long = 0,
    val defaultCurrency: String = CurrencyCode.EUR.name,
    val uiLanguage: String = LanguageCode.SYSTEM.name,
    val themeMode: String = ThemeMode.DARK.name,
)