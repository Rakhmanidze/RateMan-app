package com.currency.rateman.core.utils

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.currency.rateman.core.data.db.RateManDatabase
import com.currency.rateman.core.data.repository.SettingsRepositoryImpl
import com.currency.rateman.core.domain.app.ThemeMode
import com.currency.rateman.core.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.first

object ThemeManager {
    private fun getSettingsRepository(context: Context): SettingsRepository {
        return SettingsRepositoryImpl(RateManDatabase.getDatabase(context).settingsDao())
    }

    private suspend fun getSavedTheme(context: Context): ThemeMode {
        return getSettingsRepository(context).getSettings().first().themeMode
    }

    suspend fun initTheme(context: Context) {
            val savedTheme = getSavedTheme(context)
            setAppTheme(savedTheme)
    }

    fun setAppTheme(theme: ThemeMode) {
        val nightMode = when (theme) {
            ThemeMode.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            ThemeMode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }
}