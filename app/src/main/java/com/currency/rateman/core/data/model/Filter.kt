package com.currency.rateman.core.data.model

import android.os.Parcelable
import com.currency.rateman.core.domain.model.CurrencyCode
import com.currency.rateman.provider.data.model.ProviderType
import com.currency.rateman.core.data.model.RateSortType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Filter(
    val selectedProviderType: ProviderType,
    val targetCurrency: CurrencyCode,
    val selectedRateSortType: RateSortType
) : Parcelable