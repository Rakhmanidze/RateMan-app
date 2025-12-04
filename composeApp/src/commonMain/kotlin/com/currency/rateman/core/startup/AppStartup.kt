package com.currency.rateman.core.startup

import com.currency.rateman.api.RateFetcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object AppStartup : KoinComponent {
    private val rateFetcher: RateFetcher by inject()
    private val scope = CoroutineScope(SupervisorJob())

    fun start() {
        scope.launch {
            rateFetcher.fetchAndStoreRates()
        }
    }
}