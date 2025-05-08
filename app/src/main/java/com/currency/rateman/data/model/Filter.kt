package com.currency.rateman.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Filter(
    val selectedProviderType: ProviderType,
    val selectedCurrency: CurrencyCode,
    val selectedRateSortType: RateSortType
) : Parcelable