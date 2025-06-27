package com.currency.rateman.utils

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.currency.rateman.data.db.RateManDatabase
import com.currency.rateman.data.model.enums.ThemeMode
import com.currency.rateman.data.repository.SettingsRepository
import com.currency.rateman.data.repository.SettingsRepositoryImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object ThemeManager {

    private fun getSettingsRepository(context: Context): SettingsRepository {
        return SettingsRepositoryImpl(RateManDatabase.getDatabase(context).settingsDao())
    }

    private suspend fun getSavedTheme(context: Context): ThemeMode {
        return getSettingsRepository(context).getSettings().first().themeMode
    }

    fun initTheme(context: Context) {
        runBlocking {
            val savedTheme = getSavedTheme(context)
            setAppTheme(savedTheme)
        }
    }

    fun setAppTheme(theme: ThemeMode) {
        val nightMode = when (theme) {
            ThemeMode.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            ThemeMode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }
}