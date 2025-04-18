package com.currency.rateman.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaygroundDao {
    @Query("SELECT * FROM playground ORDER BY id ASC")
    fun getAllPlaygrounds(): Flow<List<PlaygroundEntity>>

    @Query("SELECT * FROM playground  WHERE id = :id")
    suspend fun getPlaygroundForId(id: Long): PlaygroundEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaygrounds(playgrounds: List<PlaygroundEntity>)

    @Query("DELETE FROM playground")
    suspend fun deleteAllPlaygrounds()

}