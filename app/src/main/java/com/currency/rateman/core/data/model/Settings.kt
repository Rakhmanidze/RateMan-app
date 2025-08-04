package com.currency.rateman.core.data.model

import android.os.Parcelable
import com.currency.rateman.core.data.model.enums.CurrencyCode
import com.currency.rateman.core.data.model.enums.LanguageCode
import com.currency.rateman.core.data.model.enums.ThemeMode
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Settings(
    val baseCurrency: CurrencyCode,
    val uiLanguage: LanguageCode,
    val themeMode: ThemeMode
) : Parcelable