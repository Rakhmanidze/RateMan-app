package com.currency.rateman.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.currency.rateman.data.model.RateProvider
import com.currency.rateman.data.repository.RateProviderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProvidersViewModel(private val repository: RateProviderRepository) : ViewModel() {
    private val _providers = MutableStateFlow<List<RateProvider>>(emptyList())

    val providers: StateFlow<List<RateProvider>> = _providers.asStateFlow()

    init {
        loadProviders()
    }

    private fun loadProviders() {
        _providers.value = repository.getAllProviders()
    }
}