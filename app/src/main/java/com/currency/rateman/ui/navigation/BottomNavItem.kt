package com.currency.rateman.ui.navigation

import androidx.annotation.DrawableRes

data class BottomNavItem(
    val route: String,
    @DrawableRes val iconId: Int,
    val contentDescription: String,
    val onClick: () -> Unit
)