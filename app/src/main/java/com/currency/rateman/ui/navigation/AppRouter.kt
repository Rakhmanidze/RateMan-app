package com.currency.rateman.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.currency.rateman.R
import com.currency.rateman.ui.screens.CurrencyScreen
import com.currency.rateman.ui.screens.LanguageScreen
import com.currency.rateman.ui.screens.ProviderDetailsScreen
import com.currency.rateman.ui.screens.SettingsScreen
import com.currency.rateman.ui.screens.RatesScreen
import com.currency.rateman.ui.screens.Splashscreen
import com.currency.rateman.ui.screens.BaseCurrencyScreen

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
                iconRes = R.drawable.ic_rates,
                contentDescription = "Currency rates",
                onClick = {
                    navigateToBottomNavItem(navController, Routes.Rates.route)
                }
            ),
            BottomNavItem(
                route = Routes.Settings.route,
                iconRes = R.drawable.ic_settings,
                contentDescription = "User profile",
                onClick = {
                    navigateToBottomNavItem(navController, Routes.Settings.route)
                }
            ),
        )
    }

    NavHost (
        navController = navController,
        startDestination = Routes.Rates.route
    ) {
        composable(Routes.Splash.route) {
            Splashscreen(
                onNavigate = {
                    navController.navigate(Routes.Rates.route) {
                        popUpTo(Routes.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(Routes.Rates.route) {
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
                },
                navController = navController
            )
        }
        composable(Routes.Settings.route) {
            SettingsScreen(
                bottomNavItems = bottomNavItems,
                currentRoute = currentBackStackEntry.value?.destination?.route,
                onNavItemClick = { item ->
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                navController = navController
            )
        }
        composable(
            route = "providerDetail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val providerId = backStackEntry.arguments?.getLong("id")
            ProviderDetailsScreen(
                providerId = providerId,
                navController = navController
            )
        }
        composable(Routes.Language.route) {
            LanguageScreen(
                navController = navController
            )
        }
        composable(Routes.BaseCurrency.route) {
            BaseCurrencyScreen(
                navController = navController
            )
        }
        composable(Routes.TargetCurrency.route) {
            CurrencyScreen(
                navController = navController
            )
        }
    }
}

fun navigateToBottomNavItem(navController: NavHostController, route: String) {
    navController.navigate(route) {
        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}