package com.currency.rateman.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.currency.rateman.ui.viewmodels.SettingsViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue

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
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val viewModel: SettingsViewModel = koinViewModel()
    val settings by viewModel.settings.collectAsState()

    val darkTheme = when (settings?.themeMode) {
        com.currency.rateman.data.model.ThemeMode.DARK -> true
        else -> false
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}