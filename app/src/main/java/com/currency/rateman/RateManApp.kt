package com.currency.rateman

import android.app.Application
import com.currency.rateman.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.currency.rateman.api.RateFetchWorker
import com.currency.rateman.data.repository.ProviderRepository
import com.currency.rateman.di.AppContainer
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class RateManApp: Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        AppContainer.init(applicationContext)
        startKoin {
            androidContext(this@RateManApp)
            modules(appModule)
        }
        scheduleRateFetchWorker()

        CoroutineScope(Dispatchers.IO).launch {
            val repo: ProviderRepository by inject()
            repo.refreshTopExchangeRates()
            repo.refreshAlfaPragueRates()
            repo.refreshJindrisskaExchangeRates()
        }
    }

    private fun scheduleRateFetchWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicWorkRequest = PeriodicWorkRequestBuilder<RateFetchWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        val immediateWorkRequest = OneTimeWorkRequestBuilder<RateFetchWorker>()
            .setConstraints(constraints)
            .build()

        val workManager = WorkManager.getInstance(applicationContext)

        workManager.enqueue(immediateWorkRequest)

        workManager.enqueueUniquePeriodicWork(
            "RateFetchWork",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
    }
}