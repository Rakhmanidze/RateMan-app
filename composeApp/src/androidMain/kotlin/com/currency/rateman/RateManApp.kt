package com.currency.rateman

import android.app.Application
import com.currency.rateman.core.startup.AppStartup
import com.currency.rateman.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent

class RateManApp: Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@RateManApp)
        }
        AppStartup.start()
    }
}