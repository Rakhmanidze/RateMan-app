package com.currency.rateman

import android.app.Application
import com.currency.rateman.data.db.RateManDatabase
import com.currency.rateman.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class RateManApp: Application(), KoinComponent {
    private val applicationScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            RateManDatabase.getDatabase(applicationContext).clearAllTables()
        }
        AppContainer.init(applicationContext)
        startKoin {
            androidContext(this@RateManApp)
            modules(appModule)
        }
    }
}