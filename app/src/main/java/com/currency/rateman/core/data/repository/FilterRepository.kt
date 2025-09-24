package com.currency.rateman.core.data.repository

import com.currency.rateman.core.data.model.Filter
import com.currency.rateman.core.domain.model.CurrencyCode
import com.currency.rateman.provider.data.model.ProviderType
import com.currency.rateman.core.data.model.RateSortType
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