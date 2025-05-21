package com.currency.rateman.data.repository

import com.currency.rateman.data.model.enums.CurrencyCode
import com.currency.rateman.data.model.Filter
import com.currency.rateman.data.model.enums.ProviderType
import com.currency.rateman.data.model.enums.RateSortType
import kotlinx.coroutines.flow.Flow

interface FilterRepository {
    fun getFilter(): Flow<Filter>
    suspend fun saveFilter(filter: Filter)
    suspend fun editFilters(
        selectedProviderType: ProviderType? = null,
        selectedCurrency: CurrencyCode? = null,
        selectedRateSortType: RateSortType? = null
    )
    suspend fun resetFilters()
    suspend fun ensureFiltersExist()
}