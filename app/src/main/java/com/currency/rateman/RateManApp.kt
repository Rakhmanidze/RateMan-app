package com.currency.rateman

import android.app.Application
import com.currency.rateman.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RateManApp: Application() {

    override fun onCreate() {
        super.onCreate()
        AppContainer.init(applicationContext)
        startKoin {
            androidContext(this@RateManApp)
            modules(appModule)
        }
    }
}
