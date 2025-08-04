package com.currency.rateman.di

import com.currency.rateman.data.repository.FilterRepository
import com.currency.rateman.data.repository.FilterRepositoryImpl
import com.currency.rateman.provider.domain.repository.ProviderRepository
import com.currency.rateman.provider.data.repository.ProviderRepositoryImpl
import com.currency.rateman.data.repository.SettingsRepository
import com.currency.rateman.data.repository.SettingsRepositoryImpl
import com.currency.rateman.provider.domain.usecase.GetAllProvidersUseCase
import com.currency.rateman.provider.domain.usecase.GetAllProvidersUseCaseImpl
import com.currency.rateman.ui.viewmodels.CurrencyViewModel
import com.currency.rateman.provider.ui.viewmodel.ProviderDetailViewModel
import com.currency.rateman.provider.ui.viewmodel.ProviderListViewModel
import com.currency.rateman.ui.viewmodels.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { AppContainer.rateManDatabase.currencyRateDao() }
    single { AppContainer.rateManDatabase.providerDao() }
    single { AppContainer.rateManDatabase.settingsDao() }
    single { AppContainer.rateManDatabase.filterDao() }

    single<ProviderRepository> { ProviderRepositoryImpl(get(), get()) }
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
    single <FilterRepository> { FilterRepositoryImpl(get()) }

    single<GetAllProvidersUseCase> { GetAllProvidersUseCaseImpl(get()) }

    viewModel { ProviderListViewModel(get(), get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { CurrencyViewModel() }
    viewModel { ProviderDetailViewModel(get()) }
}