package com.currency.rateman.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.currency.rateman.data.model.RateProvider
import com.currency.rateman.data.repository.RateProviderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import com.currency.rateman.data.model.ProviderType
import com.currency.rateman.data.model.RateSortType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class ProvidersViewModel(private val repository: RateProviderRepository) : ViewModel() {
    private val allProviders = repository.getAllProviders()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedProviderType = MutableStateFlow(ProviderType.ALL)
    val selectedProviderType: StateFlow<ProviderType> = _selectedProviderType.asStateFlow()

    val providers: StateFlow<List<RateProvider>> = combine(
        _searchQuery,
        _selectedProviderType

    ) { query, selectedProviderType ->
        var filteredProviders = allProviders

        if (query.isNotBlank()) {
            filteredProviders = filteredProviders.filter { provider ->
                provider.name.contains(query, ignoreCase = true)
            }
        }

        when (selectedProviderType) {
            ProviderType.ALL -> filteredProviders
            ProviderType.BANK -> filteredProviders.filter { it.type == ProviderType.BANK }
            ProviderType.EXCHANGE -> filteredProviders.filter { it.type == ProviderType.EXCHANGE }
            ProviderType.CRYPTO_EXCHANGE -> filteredProviders.filter { it.type == ProviderType.CRYPTO_EXCHANGE }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), allProviders)

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateProviderType(type: ProviderType) {
        _selectedProviderType.value = type
    }

    fun updateRateSortType(type: RateSortType) {

    }
}