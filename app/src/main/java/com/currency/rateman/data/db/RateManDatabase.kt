package com.currency.rateman.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PlaygroundEntity::class], version = 1, exportSchema = false)
abstract class RateManDatabase : RoomDatabase() {

    abstract fun playgroundDao(): PlaygroundDao

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
                    "playground_database"
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