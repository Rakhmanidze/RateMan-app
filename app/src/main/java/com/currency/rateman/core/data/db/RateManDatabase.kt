package com.currency.rateman.core.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.currency.rateman.core.data.db.dao.CurrencyRateDao
import com.currency.rateman.core.data.db.dao.FilterDao
import com.currency.rateman.core.data.db.dao.SettingsDao
import com.currency.rateman.core.data.db.entity.CurrencyRateEntity
import com.currency.rateman.core.data.db.entity.FilterEntity
import com.currency.rateman.core.data.db.entity.SettingsEntity
import com.currency.rateman.provider.data.entity.ProviderEntity
import com.currency.rateman.provider.data.dao.ProviderDao

@Database(
    entities = [
        ProviderEntity::class,
        CurrencyRateEntity::class,
        SettingsEntity::class,
        FilterEntity::class
       ],
    version = 1
)
abstract class RateManDatabase : RoomDatabase() {
    abstract  fun providerDao(): ProviderDao
    abstract fun currencyRateDao(): CurrencyRateDao
    abstract fun settingsDao(): SettingsDao
    abstract fun filterDao(): FilterDao

    companion object {
        @Volatile
        private var INSTANCE: RateManDatabase? = null

        fun getDatabase(
            context: Context,
        ): RateManDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    RateManDatabase::class.java,
                    "rateman_db"
                )
                .build()
                .also {
                    INSTANCE = it
                }
            }
        }
    }
}