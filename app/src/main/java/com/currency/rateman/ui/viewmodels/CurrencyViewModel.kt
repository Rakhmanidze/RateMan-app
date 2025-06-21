package com.currency.rateman.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currency.rateman.data.model.enums.CurrencyCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CurrencyViewModel : ViewModel() {

    private val _currencySearchQuery = MutableStateFlow("")
    val currencySearchQuery: StateFlow<String> = _currencySearchQuery.asStateFlow()

    private val allCurrencies = CurrencyCode.entries.toList()

    val filteredCurrencies: StateFlow<List<CurrencyCode>> = _currencySearchQuery
        .map { query ->
            if (query.isBlank()) {
                allCurrencies
            } else {
                allCurrencies.filter { currency ->
                    currency.name.contains(query, ignoreCase = true)
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), allCurrencies)

    fun updateCurrencySearchQuery(query: String) {
        _currencySearchQuery.value = query
    }
}