package com.currency.rateman.core.db

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.currency.rateman.core.data.db.RateManDatabase

fun getRateManDataBase(context: Context): RateManDatabase {
    val dbFile = context.getDatabasePath("rateman_db")
    return Room.databaseBuilder<RateManDatabase>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}