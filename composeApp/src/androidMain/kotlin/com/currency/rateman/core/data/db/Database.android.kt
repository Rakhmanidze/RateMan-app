package com.currency.rateman.core.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<RateManDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("rateman.db")
    return Room.databaseBuilder<RateManDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}