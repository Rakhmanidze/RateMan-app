package com.currency.rateman.core.domain.repository

import com.currency.rateman.core.domain.model.CurrencyCode
import com.currency.rateman.core.domain.model.Filter
import com.currency.rateman.core.domain.model.RateSortType
import com.currency.rateman.provider.domain.model.ProviderType
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