package com.currency.rateman.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.currency.rateman.data.db.entity.ProviderEntity
import com.currency.rateman.data.db.entity.CurrencyRateEntity
import com.currency.rateman.data.db.dao.CurrencyRateDao
import com.currency.rateman.data.db.dao.FilterDao
import com.currency.rateman.data.db.dao.ProviderDao
import com.currency.rateman.data.db.dao.SettingsDao
import com.currency.rateman.data.db.entity.FilterEntity
import com.currency.rateman.data.db.entity.SettingsEntity

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

    abstract  fun rateProviderDao(): ProviderDao
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