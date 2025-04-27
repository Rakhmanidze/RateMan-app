package com.currency.rateman.data.repository

import com.currency.rateman.data.model.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getSettings(): Flow<Settings>
    suspend fun saveSettings(settings: Settings)
    suspend fun resetSettings()
}