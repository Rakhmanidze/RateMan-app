package com.currency.rateman.core.utils

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.currency.rateman.core.domain.app.ThemeMode
import com.currency.rateman.core.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object ThemeManager: KoinComponent {
    private val settingsRepository: SettingsRepository by inject()
    private val context: Context by inject()

    private suspend fun getSavedTheme(context: Context): ThemeMode {
        return settingsRepository.getSettings().first().themeMode
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