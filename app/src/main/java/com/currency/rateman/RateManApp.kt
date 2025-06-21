package com.currency.rateman

import android.app.Application
import com.currency.rateman.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.currency.rateman.api.RateFetchWorker
import com.currency.rateman.di.AppContainer
import java.util.concurrent.TimeUnit

class RateManApp: Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        AppContainer.init(applicationContext)
        startKoin {
            androidContext(this@RateManApp)
            modules(appModule)
        }
        scheduleRateFetchWorker()
    }

    private fun scheduleRateFetchWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<RateFetchWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "RateFetchWork",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}