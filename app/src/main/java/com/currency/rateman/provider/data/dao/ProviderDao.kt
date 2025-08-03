package com.currency.rateman.provider.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.currency.rateman.data.db.entity.ProviderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProviderDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertProvider(provider: ProviderEntity): Long

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAllProvidersAndReturnIds(providers: List<ProviderEntity>): List<Long>

    @Query("SELECT * FROM rate_providers WHERE id = :id")
    suspend fun getProviderById(id: Long): ProviderEntity?

    @Query("SELECT * FROM rate_providers ORDER BY name ASC")
    fun getAllProviders(): Flow<List<ProviderEntity>>

    @Update
    suspend fun updateProvider(provider: ProviderEntity)
}