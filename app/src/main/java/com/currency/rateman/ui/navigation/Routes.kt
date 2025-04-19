package com.currency.rateman.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes (val route: String) {
    @Serializable
    data object Rates : Routes("Rates")
    @Serializable
    data object Profile : Routes("Profile")
    @Serializable
    data object Splashscreen : Routes("Splashscreen")
}