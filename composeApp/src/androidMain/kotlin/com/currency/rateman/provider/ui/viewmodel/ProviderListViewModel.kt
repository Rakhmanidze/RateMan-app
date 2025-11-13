package com.currency.rateman.provider.ui.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currency.rateman.core.domain.model.Filter
import com.currency.rateman.core.domain.model.CurrencyCode
import com.currency.rateman.provider.domain.model.ProviderType
import com.currency.rateman.core.domain.model.RateSortType
import com.currency.rateman.core.domain.repository.FilterRepository
import com.currency.rateman.provider.domain.model.Provider
import com.currency.rateman.provider.domain.usecase.FilterProvidersUseCase
import com.currency.rateman.provider.domain.usecase.GetAllProvidersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProviderListViewModel(
    private val filterRepository: FilterRepository,
    private val getAllProvidersUseCase: GetAllProvidersUseCase,
    private val filterProvidersUseCase: FilterProvidersUseCase
) : ViewModel() {
    private val allProviders = getAllProvidersUseCase.execute()
        .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5000), emptyList())

    private val _searchQuery = MutableStateFlow(TextFieldValue(""))
    val searchQuery: StateFlow<TextFieldValue> = _searchQuery.asStateFlow()

    private val _filter = MutableStateFlow<Filter?>(null)
    val filter: StateFlow<Filter?> = _filter.asStateFlow()

    val providers: StateFlow<List<Provider>> = combine(
        _searchQuery,
        _filter,
        allProviders
    ) { query, filter, providers ->
        filterProvidersUseCase.execute(
            providers,
            query.text,
            filter
        )
    }.stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            filterRepository.ensureFiltersExist()
            filterRepository.getFilter().collect { loadedFilter ->
                _filter.value = loadedFilter
            }
        }
    }

    fun updateSearchQuery(query: TextFieldValue) {
        _searchQuery.value = query
    }

    fun updateProviderType(type: ProviderType) {
        _filter.value?.let { current ->
            _filter.value = current.copy(selectedProviderType = type)
            saveFilter()
        }
    }

    fun updateCurrency(newCurrency: CurrencyCode) {
        _filter.value?.let { current ->
            _filter.value = current.copy(targetCurrency = newCurrency)
            saveFilter()
        }
    }

    fun updateRateSortType(type: RateSortType) {
        _filter.value?.let { current ->
            _filter.value = current.copy(selectedRateSortType = type)
            saveFilter()
        }
    }

    private fun saveFilter() {
        viewModelScope.launch {
            val currentFilter = _filter.value ?: Filter(
                selectedProviderType = ProviderType.ALL,
                targetCurrency = CurrencyCode.EUR,
                selectedRateSortType = RateSortType.BEST_RATE
            )
            filterRepository.editFilters(
                selectedProviderType = currentFilter.selectedProviderType,
                targetCurrency = currentFilter.targetCurrency,
                selectedRateSortType = currentFilter.selectedRateSortType
            )
        }
    }
}