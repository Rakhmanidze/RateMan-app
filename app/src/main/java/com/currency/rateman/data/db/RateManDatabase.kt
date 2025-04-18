package com.currency.rateman.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.currency.rateman.data.db.entity.RateProviderEntity
import com.currency.rateman.data.db.entity.CurrencyRateEntity
import com.currency.rateman.data.db.dao.CurrencyRateDao
import com.currency.rateman.data.db.dao.RateProviderDao

@Database(
    entities = [
        PlaygroundEntity::class,
        RateProviderEntity::class,
        CurrencyRateEntity::class,
       ],
    version = 1,
    exportSchema = false
)
abstract class RateManDatabase : RoomDatabase() {

    abstract fun playgroundDao(): PlaygroundDao
    abstract  fun rateProviderDao(): RateProviderDao
    abstract fun currencyRateDao(): CurrencyRateDao

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
                    context.applicationContext,
                    RateManDatabase::class.java,
                    "rateman.db"
                )
                .fallbackToDestructiveMigration()
                .build()
                .also {
                    INSTANCE = it
                }
            }
        }
    }
}