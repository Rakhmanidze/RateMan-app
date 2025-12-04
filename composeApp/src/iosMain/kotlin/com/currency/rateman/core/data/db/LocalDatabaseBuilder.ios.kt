package com.currency.rateman.core.data.db

import androidx.room.RoomDatabase

fun getLocalDatabaseBuilder(): RoomDatabase.Builder<RateManDatabase> {
    return getDatabaseBuilder<RateManDatabase>("rateman.db")
}