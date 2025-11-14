package com.currency.rateman

import android.app.Application
import com.currency.rateman.di.AppContainer
import com.currency.rateman.di.appModule
import com.currency.rateman.di.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class RateManApp: Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        AppContainer.init(applicationContext)
        startKoin {
            androidContext(this@RateManApp)
            modules(platformModule, appModule)
        }
    }
}