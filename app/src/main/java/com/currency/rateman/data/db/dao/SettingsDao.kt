package com.currency.rateman.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.currency.rateman.data.db.entity.SettingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: SettingsEntity): Long

    @Query("SELECT * FROM settings WHERE id = :id")
    fun getSettingsById(id: Long): Flow<SettingsEntity?>

    @Query("DELETE FROM settings")
    suspend fun deleteAllSettings()
}