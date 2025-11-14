package com.currency.rateman.core.data.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.currency.rateman.core.data.dao.CurrencyRateDao
import com.currency.rateman.core.data.dao.FilterDao
import com.currency.rateman.core.data.dao.SettingsDao
import com.currency.rateman.core.data.entity.CurrencyRateEntity
import com.currency.rateman.core.data.entity.FilterEntity
import com.currency.rateman.core.data.entity.SettingsEntity
import com.currency.rateman.provider.data.dao.ProviderDao
import com.currency.rateman.provider.data.entity.ProviderEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(
    entities = [
        ProviderEntity::class,
        CurrencyRateEntity::class,
        SettingsEntity::class,
        FilterEntity::class
    ],
    version = 1
)
@ConstructedBy(RateManDatabaseConstructor::class)
abstract class RateManDatabase : RoomDatabase() {
    abstract  fun providerDao(): ProviderDao
    abstract fun currencyRateDao(): CurrencyRateDao
    abstract fun settingsDao(): SettingsDao
    abstract fun filterDao(): FilterDao
}

@Suppress("KotlinNoActualForExpect")
expect object RateManDatabaseConstructor : RoomDatabaseConstructor<RateManDatabase> {
    override fun initialize(): RateManDatabase
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<RateManDatabase>
): RateManDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
