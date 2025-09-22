package com.currency.rateman.core.data.repository

import com.currency.rateman.core.data.dao.FilterDao
import com.currency.rateman.core.data.entity.FilterEntity
import com.currency.rateman.core.data.mappers.toFilter
import com.currency.rateman.core.data.model.Filter
import com.currency.rateman.core.data.model.enums.CurrencyCode
import com.currency.rateman.provider.data.model.ProviderType
import com.currency.rateman.core.data.model.enums.RateSortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first

class FilterRepositoryImpl(private val filterDao: FilterDao) : FilterRepository {
    override fun getFilter(): Flow<Filter> {
        return filterDao.getFilter().map { entity ->
            entity?.toFilter() ?: getDefaultFilter()
        }
    }

    override suspend fun editFilters(
        selectedProviderType: ProviderType?,
        targetCurrency: CurrencyCode?,
        selectedRateSortType: RateSortType?
    ) {
        ensureFiltersExist()

        val current = filterDao.getFilter().first() ?: return
        filterDao.updateFilter(current.copy(
            selectedProviderType = selectedProviderType?.name ?: current.selectedProviderType,
            targetCurrency = targetCurrency?.name ?: current.targetCurrency,
            selectedRateSortType = selectedRateSortType?.name ?: current.selectedRateSortType
        ))
    }

    override suspend fun ensureFiltersExist() {
        if (filterDao.getFilterCount() == 0) {
            filterDao.insertFilter(FilterEntity(id = 0))
        }
    }

    private fun getDefaultFilter(): Filter {
        return Filter(
            selectedProviderType = ProviderType.ALL,
            targetCurrency = CurrencyCode.EUR,
            selectedRateSortType = RateSortType.BEST_RATE
        )
    }
}