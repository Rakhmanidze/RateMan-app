package com.currency.rateman.core.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currency.rateman.core.data.model.CurrencyCode
import com.currency.rateman.core.domain.usecase.FilterCurrenciesUseCase
import com.currency.rateman.core.domain.usecase.GetAllCurrenciesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class CurrencyViewModel(private val getAllCurrenciesUseCase: GetAllCurrenciesUseCase, private val filterCurrenciesUseCase: FilterCurrenciesUseCase) : ViewModel() {
    private val _currencySearchQuery = MutableStateFlow("")
    val currencySearchQuery: StateFlow<String> = _currencySearchQuery.asStateFlow()

    private val allCurrencies = getAllCurrenciesUseCase.execute()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredCurrencies: StateFlow<List<CurrencyCode>> = _currencySearchQuery
        .combine(allCurrencies) { query, allCurrencies ->
            filterCurrenciesUseCase.execute(query, allCurrencies)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateCurrencySearchQuery(query: String) {
        _currencySearchQuery.value = query
    } 
}