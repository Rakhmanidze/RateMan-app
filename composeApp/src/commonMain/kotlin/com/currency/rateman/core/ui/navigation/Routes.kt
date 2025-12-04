package com.currency.rateman.core.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {
    @Serializable
    data object Rates : Routes

    @Serializable
    data object Settings : Routes

    @Serializable
    data object Splash : Routes

    @Serializable
    data object Language : Routes

    @Serializable
    data object TargetCurrency : Routes

    @Serializable
    data object BaseCurrency : Routes

    @Serializable
    data class ProviderDetail(val id: Long) : Routes
}