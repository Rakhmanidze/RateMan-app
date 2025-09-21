package com.currency.rateman.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.currency.rateman.core.ui.viewmodels.SettingsViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import com.currency.rateman.core.domain.app.ThemeMode

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    secondary = DarkSecondary,
    tertiary = DarkTertiary,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = DarkOnPrimary,
    onSurface = DarkOnBackground,
    onBackground = DarkOnBackground
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    secondary = LightSecondary,
    tertiary = LightTertiary,
    background = LightBackground,
    surface = LightSurface,
    onPrimary = LightOnPrimary,
    onSurface = LightOnBackground,
    onBackground = LightOnBackground
)

@Composable
fun RateManAppTheme(
    content: @Composable () -> Unit
) {
    val viewModel: SettingsViewModel = koinViewModel()
    val settings by viewModel.settings.collectAsState()

    val colorScheme = when (settings?.themeMode) {
        ThemeMode.DARK -> DarkColorScheme
        ThemeMode.LIGHT -> LightColorScheme
        null -> DarkColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}