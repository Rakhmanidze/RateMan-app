package com.currency.rateman

import android.app.Application

class RateManApp: Application() {

    override fun onCreate() {
        super.onCreate()
        AppContainer.init(this)
    }
}
