package com.currency.rateman

import android.content.Context
import com.currency.rateman.data.db.RateManDatabase

object AppContainer {
    lateinit var ratemanDatabase : RateManDatabase
        private set

    fun init(context: Context) {
        ratemanDatabase = RateManDatabase.getDatabase(context)
    }
}