package com.currency.rateman.di

import com.currency.rateman.data.repository.RateProviderRepository
import com.currency.rateman.data.repository.RateProviderRepositoryFake
import com.currency.rateman.ui.viewmodels.ProvidersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { ProvidersViewModel(get()) }
}

val repositoryModule = module {
    single<RateProviderRepository> { RateProviderRepositoryFake() }
}