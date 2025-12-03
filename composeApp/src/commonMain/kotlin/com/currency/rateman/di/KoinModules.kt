package com.currency.rateman.di

import com.currency.rateman.api.RateFetcher
import com.currency.rateman.core.data.db.RateManDatabase
import com.currency.rateman.core.data.repository.FilterRepositoryImpl
import com.currency.rateman.core.data.repository.SettingsRepositoryImpl
import com.currency.rateman.core.domain.repository.FilterRepository
import com.currency.rateman.core.domain.repository.SettingsRepository
import com.currency.rateman.core.domain.usecase.FilterCurrenciesUseCase
import com.currency.rateman.core.domain.usecase.FilterCurrenciesUseCaseImpl
import com.currency.rateman.core.domain.usecase.GetAllCurrenciesUseCase
import com.currency.rateman.core.domain.usecase.GetAllCurrenciesUseCaseImpl
import com.currency.rateman.core.ui.viewmodel.CurrencyViewModel
import com.currency.rateman.core.ui.viewmodel.SettingsViewModel
import com.currency.rateman.provider.data.repository.ProviderRepositoryImpl
import com.currency.rateman.provider.domain.repository.ProviderRepository
import com.currency.rateman.provider.domain.usecase.FilterProvidersUseCase
import com.currency.rateman.provider.domain.usecase.FilterProvidersUseCaseImpl
import com.currency.rateman.provider.domain.usecase.GetAllProvidersUseCase
import com.currency.rateman.provider.domain.usecase.GetAllProvidersUseCaseImpl
import com.currency.rateman.provider.domain.usecase.GetProviderByIdUseCase
import com.currency.rateman.provider.domain.usecase.GetProviderByIdUseCaseImpl
import com.currency.rateman.provider.ui.viewmodel.ProviderDetailViewModel
import com.currency.rateman.provider.ui.viewmodel.ProviderListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    /* ---------- DAOs ---------- */
    single { get<RateManDatabase>().currencyRateDao() }
    single {  get<RateManDatabase>().providerDao() }
    single {  get<RateManDatabase>().settingsDao() }
    single {  get<RateManDatabase>().filterDao() }

    /* ---------- Repositories ---------- */

    single<ProviderRepository> { ProviderRepositoryImpl(providerDao = get(), currencyRateDao = get()) }
    single<SettingsRepository> { SettingsRepositoryImpl(settingsDao = get()) }
    single <FilterRepository> { FilterRepositoryImpl(filterDao = get()) }

    /* ---------- Use Cases ---------- */

    single<GetAllProvidersUseCase> { GetAllProvidersUseCaseImpl(providerRepository = get()) }
    single<GetProviderByIdUseCase> { GetProviderByIdUseCaseImpl(providerRepository = get()) }
    single<FilterProvidersUseCase> { FilterProvidersUseCaseImpl() }
    single<GetAllCurrenciesUseCase> { GetAllCurrenciesUseCaseImpl() }
    single<FilterCurrenciesUseCase> { FilterCurrenciesUseCaseImpl() }

    /* ---------- View Models ---------- */

    viewModel { ProviderListViewModel(filterRepository = get(), getAllProvidersUseCase =  get(), filterProvidersUseCase = get()) }
    viewModel { SettingsViewModel(repository = get()) }
    viewModel { CurrencyViewModel(getAllCurrenciesUseCase = get(), filterCurrenciesUseCase = get()) }
    viewModel { ProviderDetailViewModel(getProviderByIdUseCase = get()) }

    /* ---------- Other ---------- */

    single<RateFetcher> { RateFetcher(httpClient = get(), providerRepository = get()) }
}