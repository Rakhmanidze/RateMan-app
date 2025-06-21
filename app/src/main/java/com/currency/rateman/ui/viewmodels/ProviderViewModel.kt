package com.currency.rateman.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currency.rateman.data.model.RateProvider
import com.currency.rateman.data.repository.ProviderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProviderViewModel(
    private val providerRepository: ProviderRepository,
) : ViewModel() {

    private val _provider = MutableStateFlow<RateProvider?>(null)
    val provider: StateFlow<RateProvider?> = _provider.asStateFlow()

    fun getProviderById(id: Long) {
        viewModelScope.launch {
            val provider = providerRepository.getProviderById(id)
            _provider.value = provider
        }
    }
}