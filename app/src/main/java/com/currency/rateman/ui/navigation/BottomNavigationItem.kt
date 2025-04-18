package com.currency.rateman.ui.navigation

import androidx.annotation.DrawableRes

data class BottomNavigationItem(
    val route: String,
    val label: String,
    @DrawableRes val iconId: Int,
    val contentDescription: String,
    val onClick: () -> Unit
)
