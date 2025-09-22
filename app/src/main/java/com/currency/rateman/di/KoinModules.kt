package com.currency.rateman.di

import com.currency.rateman.core.data.repository.FilterRepository
import com.currency.rateman.core.data.repository.FilterRepositoryImpl
import com.currency.rateman.provider.domain.repository.ProviderRepository
import com.currency.rateman.provider.data.repository.ProviderRepositoryImpl
import com.currency.rateman.core.data.repository.SettingsRepository
import com.currency.rateman.core.data.repository.SettingsRepositoryImpl
import com.currency.rateman.core.domain.usecase.GetAllCurrenciesUseCase
import com.currency.rateman.core.domain.usecase.GetAllCurrenciesUseCaseImpl
import com.currency.rateman.provider.domain.usecase.GetAllProvidersUseCase
import com.currency.rateman.provider.domain.usecase.GetAllProvidersUseCaseImpl
import com.currency.rateman.core.ui.viewmodels.CurrencyViewModel
import com.currency.rateman.provider.ui.viewmodel.ProviderDetailViewModel
import com.currency.rateman.provider.ui.viewmodel.ProviderListViewModel
import com.currency.rateman.core.ui.viewmodels.SettingsViewModel
import com.currency.rateman.provider.domain.usecase.FilterProvidersUseCase
import com.currency.rateman.provider.domain.usecase.FilterProvidersUseCaseImpl
import com.currency.rateman.provider.domain.usecase.GetProviderByIdUseCase
import com.currency.rateman.provider.domain.usecase.GetProviderByIdUseCaseImpl
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
    single<GetProviderByIdUseCase> { GetProviderByIdUseCaseImpl(get()) }
    single<FilterProvidersUseCase> { FilterProvidersUseCaseImpl() }
    single<GetAllCurrenciesUseCase> { GetAllCurrenciesUseCaseImpl() }

    viewModel { ProviderListViewModel(get(), get(), get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { CurrencyViewModel(get()) }
    viewModel { ProviderDetailViewModel(get()) }
}