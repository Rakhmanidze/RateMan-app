package com.currency.rateman.provider.ui.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currency.rateman.data.model.Filter
import com.currency.rateman.data.model.enums.CurrencyCode
import com.currency.rateman.data.model.enums.ProviderType
import com.currency.rateman.data.model.enums.RateSortType
import com.currency.rateman.data.repository.FilterRepository
import com.currency.rateman.provider.data.model.Provider
import com.currency.rateman.provider.domain.repository.ProviderRepository
import com.currency.rateman.provider.domain.usecase.GetAllProvidersUseCaseImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProviderListViewModel(
    private val providerRepository: ProviderRepository,
    private val filterRepository: FilterRepository,
    private val getAllProvidersUseCase: GetAllProvidersUseCaseImpl
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
        var filteredProviders = providers

        if (query.text.isNotBlank()) {
            filteredProviders = filteredProviders.filter { provider ->
                provider.name.contains(query.text, ignoreCase = true)
            }
        }
        if (filter?.selectedProviderType != null) {
            filteredProviders = when (filter.selectedProviderType) {
                ProviderType.ALL -> filteredProviders
                ProviderType.BANK -> filteredProviders.filter { it.type == ProviderType.BANK }
                ProviderType.EXCHANGE -> filteredProviders.filter { it.type == ProviderType.EXCHANGE }
                ProviderType.CRYPTO_EXCHANGE -> filteredProviders.filter { it.type == ProviderType.CRYPTO_EXCHANGE }
            }
        }
        if (filter?.targetCurrency != null) {
            filteredProviders = filteredProviders.filter { provider ->
                provider.rates.any { rate -> rate.foreignCurrency == filter.targetCurrency }
            }
            filteredProviders = when (filter.selectedRateSortType) {
                RateSortType.BEST_SELL -> filteredProviders.sortedBy { provider ->
                    provider.rates.firstOrNull { it.foreignCurrency == filter.targetCurrency }?.sellRate
                        ?: Double.POSITIVE_INFINITY
                }

                RateSortType.BEST_BUY -> filteredProviders.sortedByDescending { provider ->
                    provider.rates.firstOrNull { it.foreignCurrency == filter.targetCurrency }?.buyRate
                        ?: Double.NEGATIVE_INFINITY
                }

                RateSortType.BEST_RATE -> filteredProviders.sortedBy { it.name }
            }
        }

        filteredProviders
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