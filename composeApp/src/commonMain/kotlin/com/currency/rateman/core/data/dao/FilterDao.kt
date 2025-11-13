package com.currency.rateman.core.data.dao

import com.currency.rateman.core.data.entity.FilterEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FilterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilter(filter: FilterEntity)

    @Update
    suspend fun updateFilter(filter: FilterEntity)

    @Query("SELECT * FROM filters WHERE id = 0")
    fun getFilter(): Flow<FilterEntity?>

    @Query("SELECT COUNT(*) FROM filters")
    suspend fun getFilterCount(): Int
}