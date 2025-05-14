package com.currency.rateman.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes (val route: String) {
    @Serializable
    data object Rates : Routes("rates")
    @Serializable
    data object Settings : Routes("settings")
    @Serializable
    data object Splash : Routes("splash")
    @Serializable
    data object Language : Routes("language")
}