package com.currency.rateman.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Profile(
    val defaultCurrency: CurrencyCode = CurrencyCode.CZK,
    val uiLanguage: LanguageCode = LanguageCode.EN,
    val themeMode: ThemeMode = ThemeMode.DARK,
) : Parcelable