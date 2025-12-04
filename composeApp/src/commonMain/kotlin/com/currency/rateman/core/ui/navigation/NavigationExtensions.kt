package com.currency.rateman.core.ui.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateToBottomTab(route: Routes) {
    navigate(route) {
        popUpTo(graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}