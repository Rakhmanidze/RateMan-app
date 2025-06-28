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
    @Serializable
    data object Currency : Routes("currency")
}

//TODO:Rename default currency to base currency to more clear name.
// after it version of db increase by 1TODO: rename all default to
// base in all languages which i have