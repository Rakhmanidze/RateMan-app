package com.currency.rateman.provider.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currency.rateman.core.domain.model.CurrencyCode
import com.currency.rateman.provider.domain.model.Provider
import com.currency.rateman.provider.domain.usecase.GetProviderByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProviderDetailViewModel(private val getProviderByIdUseCase: GetProviderByIdUseCase) : ViewModel() {
    private val _provider = MutableStateFlow<Provider?>(null)
    val provider: StateFlow<Provider?> = _provider.asStateFlow()

    fun getProviderById(id: Long) {
        viewModelScope.launch {
            val provider = getProviderByIdUseCase.execute(id)
            _provider.value = provider

            _provider.value = provider?.copy(
                rates = provider.rates
                    .filter { it.buyRate != 0.0 || it.sellRate != 0.0 }
                    .sortedBy { rate ->
                        CurrencyCode.entries.toTypedArray().indexOfFirst { it == rate.foreignCurrency }
                    }
            )
        }
    }
}