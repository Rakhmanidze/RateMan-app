package com.currency.rateman.data.model

import android.os.Parcelable
import com.currency.rateman.data.model.enums.CurrencyCode
import com.currency.rateman.data.model.enums.ProviderType
import com.currency.rateman.data.model.enums.RateSortType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Filter(
    val selectedProviderType: ProviderType,
    val targetCurrency: CurrencyCode,
    val selectedRateSortType: RateSortType
) : Parcelable