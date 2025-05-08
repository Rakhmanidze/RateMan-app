package com.currency.rateman.data.repository

import com.currency.rateman.data.model.CurrencyCode
import com.currency.rateman.data.model.Filter
import com.currency.rateman.data.model.ProviderType
import com.currency.rateman.data.model.RateSortType
import kotlinx.coroutines.flow.Flow

interface FilterRepository {
    fun getFilter(): Flow<Filter>
    suspend fun saveFilter(filter: Filter)
    suspend fun resetFilters()
    suspend fun editFilters(
        selectedProviderType: ProviderType? = null,
        selectedCurrency: CurrencyCode? = null,
        selectedRateSortType: RateSortType? = null
    )
    suspend fun ensureFiltersExist()
}