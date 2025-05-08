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

    private val _selectedProviderType = MutableStateFlow(ProviderType.ALL)
    val selectedProviderType: StateFlow<ProviderType> = _selectedProviderType.asStateFlow()

    private val _selectedCurrency = MutableStateFlow(CurrencyCode.EUR)
    val selectedCurrency: StateFlow<CurrencyCode> = _selectedCurrency.asStateFlow()

    private val _selectedRateSortType = MutableStateFlow(RateSortType.BEST_RATE)
    val selectedRateSortType: StateFlow<RateSortType> = _selectedRateSortType.asStateFlow()

    val providers: StateFlow<List<RateProvider>> = combine(
        _searchQuery,
        _selectedCurrency,
        _selectedProviderType

    ) { query, selectedCurrency, selectedProviderType ->
        var filteredProviders = allProviders

        if (query.text.isNotBlank()) {
            filteredProviders = filteredProviders.filter { provider ->
                provider.name.contains(query.text, ignoreCase = true)
            }
        }

        filteredProviders = when (selectedProviderType) {
            ProviderType.ALL -> filteredProviders
            ProviderType.BANK -> filteredProviders.filter { it.type == ProviderType.BANK }
            ProviderType.EXCHANGE -> filteredProviders.filter { it.type == ProviderType.EXCHANGE }
            ProviderType.CRYPTO_EXCHANGE -> filteredProviders.filter { it.type == ProviderType.CRYPTO_EXCHANGE }
        }

        filteredProviders = filteredProviders.filter { provider ->
            provider.rates.any {rate -> rate.foreignCurrency == selectedCurrency }
        }
        filteredProviders
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), allProviders)

    init {
        viewModelScope.launch {
            filterRepository.ensureFiltersExist()
            filterRepository.getFilter().collect { filter ->
                _selectedProviderType.value = filter.selectedProviderType
                _selectedCurrency.value = filter.selectedCurrency
                _selectedRateSortType.value = filter.selectedRateSortType
            }
        }
    }

    fun updateSearchQuery(query: TextFieldValue) {
        _searchQuery.value = query
    }

    fun updateProviderType(type: ProviderType) {
        _selectedProviderType.value = type
        saveFilter()
    }

    fun updateCurrency(newCurrency: CurrencyCode) {
        _selectedCurrency.value = newCurrency
        saveFilter()
    }

    fun updateRateSortType(type: RateSortType) {
        _selectedRateSortType.value = type
        saveFilter()
    }

    private fun saveFilter() {
        viewModelScope.launch {
            filterRepository.editFilters(
                selectedProviderType = _selectedProviderType.value,
                selectedCurrency = _selectedCurrency.value,
                selectedRateSortType = _selectedRateSortType.value
            )
        }
    }
}