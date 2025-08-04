package com.currency.rateman.core.data.model

import android.os.Parcelable
import com.currency.rateman.core.data.model.enums.CurrencyCode
import com.currency.rateman.core.data.model.enums.ProviderType
import com.currency.rateman.core.data.model.enums.RateSortType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Filter(
    val selectedProviderType: ProviderType,
    val targetCurrency: CurrencyCode,
    val selectedRateSortType: RateSortType
) : Parcelable