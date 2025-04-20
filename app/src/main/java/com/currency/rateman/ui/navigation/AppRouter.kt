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
import com.currency.rateman.ui.screens.ProfileScreen
import com.currency.rateman.ui.screens.RatesScreen
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
        startDestination = Routes.Rates
    ) {
        composable<Routes.Splashscreen>() {
            Splashscreen(
                onNavigate = {
                    navController.navigate(Routes.Rates) {
                        popUpTo(Routes.Splashscreen) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Routes.Rates>() {
            RatesScreen(
                bottomNavItems  = bottomNavItems,
                currentRoute = currentBackStackEntry.value?.destination?.route,
                onNavItemClick = { item ->
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        composable<Routes.Profile>() {
            ProfileScreen(
                bottomNavItems  = bottomNavItems,
                currentRoute = currentBackStackEntry.value?.destination?.route,
                profile = profile,
                onNavItemClick = { item ->
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
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