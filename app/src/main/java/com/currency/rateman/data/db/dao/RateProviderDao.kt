package com.currency.rateman.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.currency.rateman.data.db.entity.RateProviderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RateProviderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProvider(provider: RateProviderEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProvidersAndReturnIds(providers: List<RateProviderEntity>): List<Long>

    @Query("SELECT * FROM rate_providers WHERE id = :id")
    suspend fun getProviderById(id: Long): RateProviderEntity?

    @Query("SELECT * FROM rate_providers ORDER BY name ASC")
    fun getAllProviders(): Flow<List<RateProviderEntity>>

    @Update
    suspend fun updateProvider(provider: RateProviderEntity)
}