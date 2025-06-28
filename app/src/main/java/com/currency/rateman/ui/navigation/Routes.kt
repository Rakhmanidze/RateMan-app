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
    @Serializable
    data object BaseCurrency : Routes("baseCurrency")
}

//TODO:Rename default currency to base currency to more clear name.
// after it version of db increase by 1TODO: rename all default to
// base in all languages which i have

//TODO: Rename CurrencyScreen to TargetCurrency and variable currency to targetCurrency

//TODO: in base currency screen have title saying choose base currency and in targetcurrency
// screen say choose target or something instead of target but not just choose currency

//TODO: rename in currency screen and base view model to currencyviewmodel variable

//TODO: rename selectedCurrency variable to targetCurrency in filter entity and class