package com.currency.rateman.data.repository

import com.currency.rateman.data.db.dao.FilterDao
import com.currency.rateman.data.db.entity.FilterEntity
import com.currency.rateman.data.model.enums.CurrencyCode
import com.currency.rateman.data.model.Filter
import com.currency.rateman.data.model.enums.ProviderType
import com.currency.rateman.data.model.enums.RateSortType
import com.currency.rateman.data.mappers.toFilter
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
        selectedCurrency: CurrencyCode?,
        selectedRateSortType: RateSortType?
    ) {
        ensureFiltersExist()

        val current = filterDao.getFilter().first() ?: return
        filterDao.updateFilter(current.copy(
            selectedProviderType = selectedProviderType?.name ?: current.selectedProviderType,
            selectedCurrency = selectedCurrency?.name ?: current.selectedCurrency,
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
            selectedCurrency = CurrencyCode.EUR,
            selectedRateSortType = RateSortType.BEST_RATE
        )
    }
}