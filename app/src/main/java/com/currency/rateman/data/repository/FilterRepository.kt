package com.currency.rateman.data.repository

import com.currency.rateman.data.model.enums.CurrencyCode
import com.currency.rateman.data.model.Filter
import com.currency.rateman.data.model.enums.ProviderType
import com.currency.rateman.data.model.enums.RateSortType
import kotlinx.coroutines.flow.Flow

interface FilterRepository {
    fun getFilter(): Flow<Filter>

    suspend fun editFilters(
        selectedProviderType: ProviderType? = null,
        targetCurrency: CurrencyCode? = null,
        selectedRateSortType: RateSortType? = null
    )

    suspend fun ensureFiltersExist()
}