package com.currency.rateman.core.ui

import androidx.compose.runtime.Composable
import com.currency.rateman.core.ui.navigation.AppRouter
import com.currency.rateman.core.ui.theme.RateManAppTheme

@Composable
fun App() {
    RateManAppTheme {
        AppRouter()
    }
}