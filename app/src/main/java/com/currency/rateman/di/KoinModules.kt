package com.currency.rateman.di

import com.currency.rateman.AppContainer
import com.currency.rateman.data.repository.FilterRepository
import com.currency.rateman.data.repository.FilterRepositoryImpl
import com.currency.rateman.data.repository.RateProviderRepository
import com.currency.rateman.data.repository.RateProviderRepositoryFake
import com.currency.rateman.data.repository.SettingsRepository
import com.currency.rateman.data.repository.SettingsRepositoryImpl
import com.currency.rateman.ui.viewmodels.CurrencySelectionViewModel
import com.currency.rateman.ui.viewmodels.RatesViewModel
import com.currency.rateman.ui.viewmodels.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { AppContainer.rateManDatabase.settingsDao() }
    single { AppContainer.rateManDatabase.filterDao() }

    single<RateProviderRepository> { RateProviderRepositoryFake() }
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
    single <FilterRepository> { FilterRepositoryImpl(get()) }

    viewModel { RatesViewModel(get(), get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { CurrencySelectionViewModel() }

}