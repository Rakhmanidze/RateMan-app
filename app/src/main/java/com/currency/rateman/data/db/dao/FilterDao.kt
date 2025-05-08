package com.currency.rateman.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.currency.rateman.data.db.entity.FilterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FilterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilter(filter: FilterEntity)

    @Update
    suspend fun updateFilter(filter: FilterEntity)

    @Query("SELECT * FROM filters WHERE id = 0")
    fun getFilter(): Flow<FilterEntity?>

    @Query("DELETE FROM filters")
    suspend fun deleteAllFilters()

    @Query("SELECT COUNT(*) FROM filters")
    suspend fun getFilterCount(): Int
}