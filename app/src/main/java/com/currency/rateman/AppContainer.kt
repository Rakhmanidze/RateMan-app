package com.currency.rateman

import android.content.Context
import com.currency.rateman.data.db.RateManDatabase

object AppContainer {
    lateinit var rateManDatabase : RateManDatabase
        private set

    fun init(context: Context) {
        rateManDatabase = RateManDatabase.getDatabase(context)
    }
}