package com.currency.rateman.core.ui.navigation

import androidx.annotation.DrawableRes

data class BottomNavItem(
    val route: String,
    @DrawableRes val iconRes: Int,
    val contentDescription: String,
    val onClick: () -> Unit
)