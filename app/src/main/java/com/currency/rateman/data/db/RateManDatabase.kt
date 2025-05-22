package com.currency.rateman.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.currency.rateman.data.db.entity.RateProviderEntity
import com.currency.rateman.data.db.entity.CurrencyRateEntity
import com.currency.rateman.data.db.dao.CurrencyRateDao
import com.currency.rateman.data.db.dao.FilterDao
import com.currency.rateman.data.db.dao.RateProviderDao
import com.currency.rateman.data.db.dao.SettingsDao
import com.currency.rateman.data.db.entity.FilterEntity
import com.currency.rateman.data.db.entity.SettingsEntity

@Database(
    entities = [
        RateProviderEntity::class,
        CurrencyRateEntity::class,
        SettingsEntity::class,
        FilterEntity::class
       ],
    version = 1
)
abstract class RateManDatabase : RoomDatabase() {

    abstract  fun rateProviderDao(): RateProviderDao
    abstract fun currencyRateDao(): CurrencyRateDao
    abstract fun settingsDao(): SettingsDao
    abstract fun filterDao(): FilterDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: RateManDatabase? = null

        fun getDatabase(
            context: Context,
            //scope: CoroutineScope - for coroutines
        ): RateManDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    RateManDatabase::class.java,
                    "rateman_db"
                )
//                .fallbackToDestructiveMigration()
                .build()
                .also {
                    INSTANCE = it
                }
            }
        }
    }
}