package com.currency.rateman.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.currency.rateman.data.model.enums.CurrencyCode
import com.currency.rateman.data.model.enums.LanguageCode
import com.currency.rateman.data.model.enums.ThemeMode

@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey
    val id: Long = 0,
    val baseCurrency: String = CurrencyCode.EUR.name,
    val uiLanguage: String = LanguageCode.EN.name,
    val themeMode: String = ThemeMode.DARK.name,
)