package com.currency.rateman.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes (val route: String) {
    @Serializable
    data object Countdown : Routes("Countdown")
    @Serializable
    data object PlaygroundList : Routes("PlaygroundList")
    @Serializable
    data class PlaygroundEditor(val id: Long) : Routes("PlaygroundList")
    @Serializable
    data object UserProfile : Routes("UserProfile")
    @Serializable
    data object Splashscreen : Routes("Splashscreen")
    @Serializable
    data class UserProfileEditor (
        val name: String,
        val surname: String,
        val noOfKids: Int
    ) : Routes("UserProfileEditor")
}