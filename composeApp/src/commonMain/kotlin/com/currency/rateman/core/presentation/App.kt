package com.currency.rateman.core.presentation

import androidx.compose.runtime.Composable
import com.currency.rateman.core.presentation.navigation.AppRouter
import com.currency.rateman.core.presentation.theme.RateManAppTheme

@Composable
fun App() {
    RateManAppTheme {
        AppRouter()
    }
}