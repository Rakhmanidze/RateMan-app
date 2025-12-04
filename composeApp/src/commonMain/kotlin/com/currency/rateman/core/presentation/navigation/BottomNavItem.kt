package com.currency.rateman.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

data class BottomNavItem(
    val route: Routes,
    val icon: @Composable () -> Painter,
    val contentDescription: String
)