package com.currency.rateman.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.currency.rateman.R
import com.currency.rateman.data.model.CurrencyCode
import com.currency.rateman.data.model.LanguageCode
import com.currency.rateman.data.model.Profile
import com.currency.rateman.data.model.ThemeMode
import com.currency.rateman.ui.screens.CountdownScreen
import com.currency.rateman.ui.screens.PlaygroundEditorScreen
import com.currency.rateman.ui.screens.PlaygroundListScreen
import com.currency.rateman.ui.screens.Splashscreen

@Composable
fun AppRouter() {
    val navController = rememberNavController()

    MainAppRouter(
        navController = navController,
    )
}

@Composable
fun MainAppRouter(navController: NavHostController) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()

    val bottomNavItems = remember {
        listOf(
            BottomNavItem(
                route = Routes.Rates.route,
                label = "Rates",
                iconId = R.drawable.swing_icon,
                contentDescription = "Currency rates",
                onClick = {
                    navigateToBottomNavItem(navController, Routes.Rates)
                }
            ),
            BottomNavItem(
                route = Routes.Profile.route,
                label = "Profile",
                iconId = R.drawable.person_24px,
                contentDescription = "User profile",
                onClick = {
                    navigateToBottomNavItem(navController, Routes.Profile)
                }
            ),
        )
    }

    var profile by rememberSaveable { mutableStateOf(
        Profile (
            defaultCurrency = CurrencyCode.CZK,
            uiLanguage = LanguageCode.EN,
            themeMode = ThemeMode.DARK
        )
    ) }

    NavHost (
        navController = navController,
        startDestination = Routes.Splashscreen
    ) {
        composable<Routes.Splashscreen>() {
            Splashscreen(
                onNavigate = {
                    navController.navigate(Routes.PlaygroundList) {
                        popUpTo(Routes.Splashscreen) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Routes.Countdown>() {
            CountdownScreen(
                bottomNavItems,
                currentBackStackEntry.value?.destination?.route,
            )
        }
        composable<Routes.PlaygroundList>() {
            PlaygroundListScreen(
                bottomNavItems,
                currentBackStackEntry.value?.destination?.route,
                onItemClick = {
                    navController.navigate(Routes.PlaygroundEditor(it))
                }
            )
        }
        composable<Routes.UserProfile>() {
            UserProfileScreen(
                bottomNavItems,
                currentBackStackEntry.value?.destination?.route,
                profile,
                {
                    navController.navigate(Routes.UserProfileEditor(
                        profile.name,
                        profile.surname,
                        profile.numberOfKids
                    ))
                }
            )
        }
        composable<Routes.UserProfileEditor>() {
            UserProfileEditorScreen(
                saveUserProfile = {
                    profile = it
                    navController.popBackStack()
                },
                cancelEditing = {
                    navController.popBackStack()
                }
            )
        }
        composable<Routes.PlaygroundEditor> {
            PlaygroundEditorScreen(
                savePlayground = {
                    navController.popBackStack()
                },
                cancelEditing = {
                    navController.popBackStack()
                }
            )
        }
    }
}

fun navigateToBottomNavItem(navController: NavHostController, route: Routes) {
    navController.navigate(route) {
        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}