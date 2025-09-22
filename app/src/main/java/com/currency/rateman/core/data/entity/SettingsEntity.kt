package com.currency.rateman.core.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.currency.rateman.core.data.model.CurrencyCode
import com.currency.rateman.core.domain.app.LanguageCode
import com.currency.rateman.core.domain.app.ThemeMode

@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey
    val id: Long = 0,
    val baseCurrency: String = CurrencyCode.EUR.name,
    val uiLanguage: String = LanguageCode.EN.name,
    val themeMode: String = ThemeMode.DARK.name,
)