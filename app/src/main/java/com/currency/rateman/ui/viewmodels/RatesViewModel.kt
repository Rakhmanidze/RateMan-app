package com.currency.rateman.ui.viewmodels

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.currency.rateman.data.model.RateProvider
import com.currency.rateman.data.repository.RateProviderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import com.currency.rateman.data.model.CurrencyCode
import com.currency.rateman.data.model.Filter
import com.currency.rateman.data.model.ProviderType
import com.currency.rateman.data.model.RateSortType
import com.currency.rateman.data.repository.FilterRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RatesViewModel(
    private val rateProviderRepository: RateProviderRepository,
    private val filterRepository: FilterRepository
    ) : ViewModel() {
    private val allProviders = rateProviderRepository.getAllProviders()

    private val _searchQuery = MutableStateFlow(TextFieldValue(""))
    val searchQuery: StateFlow<TextFieldValue> = _searchQuery.asStateFlow()

    private val _filter = MutableStateFlow<Filter?>(null)
    val filter: StateFlow<Filter?> = _filter.asStateFlow()

    val providers: StateFlow<List<RateProvider>> = combine(
        _searchQuery,
        _filter
    ) { query, filter ->
        var filteredProviders = allProviders

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
        if (filter?.selectedCurrency != null) {
            filteredProviders = filteredProviders.filter { provider ->
                provider.rates.any { rate -> rate.foreignCurrency == filter.selectedCurrency }
            }
            filteredProviders = when (filter.selectedRateSortType) {
                RateSortType.BEST_BUY -> filteredProviders.sortedBy { provider ->
                    provider.rates.firstOrNull { it.foreignCurrency == filter.selectedCurrency }?.buyRate ?: Double.POSITIVE_INFINITY
                }
                RateSortType.BEST_SELL -> filteredProviders.sortedByDescending { provider ->
                    provider.rates.firstOrNull { it.foreignCurrency == filter.selectedCurrency }?.sellRate ?: Double.NEGATIVE_INFINITY
                }
                RateSortType.BEST_RATE ->  filteredProviders
            }
        }

        filteredProviders
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), allProviders)

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
            _filter.value = current.copy(selectedCurrency = newCurrency)
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
                selectedCurrency = CurrencyCode.EUR,
                selectedRateSortType = RateSortType.BEST_RATE
            )
            filterRepository.editFilters(
                selectedProviderType = currentFilter.selectedProviderType,
                selectedCurrency = currentFilter.selectedCurrency,
                selectedRateSortType = currentFilter.selectedRateSortType
            )
        }
    }
}