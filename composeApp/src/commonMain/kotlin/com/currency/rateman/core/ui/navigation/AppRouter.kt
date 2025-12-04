package com.currency.rateman.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.currency.rateman.core.ui.screen.BaseCurrencyScreen
import com.currency.rateman.core.ui.screen.SettingsScreen
import com.currency.rateman.core.ui.screen.Splashscreen
import com.currency.rateman.core.ui.screen.TargetCurrencyScreen
import com.currency.rateman.provider.ui.screen.ProviderDetailScreen
import com.currency.rateman.provider.ui.screen.ProviderListScreen
import org.jetbrains.compose.resources.painterResource
import rateman.composeapp.generated.resources.Res
import rateman.composeapp.generated.resources.ic_rates
import rateman.composeapp.generated.resources.ic_settings

@Composable
fun AppRouter() {
    val navController = rememberNavController()
    MainAppRouter(navController = navController)
}

@Composable
fun MainAppRouter(navController: NavHostController) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()

    val bottomNavItems = remember {
        listOf(
            BottomNavItem(
                route = Routes.Rates,
                icon = { painterResource(Res.drawable.ic_rates) },
                contentDescription = "Currency rates"
            ),
            BottomNavItem(
                route = Routes.Settings,
                icon = { painterResource(Res.drawable.ic_settings) },
                contentDescription = "User profile"
            ),
        )
    }

    NavHost(
        navController = navController,
        startDestination = Routes.Rates
    ) {
        composable<Routes.Splash> {
            Splashscreen(
                onNavigate = {
                    navController.navigate(Routes.Rates) {
                        popUpTo<Routes.Splash> {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<Routes.Rates> {
            ProviderListScreen(
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

        composable<Routes.Settings> {
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

        composable<Routes.ProviderDetail> {
            val args = it.toRoute<Routes.ProviderDetail>()
            ProviderDetailScreen(
                providerId = args.id,
                navController = navController
            )
        }

        composable<Routes.BaseCurrency> {
            BaseCurrencyScreen(
                navController = navController
            )
        }

        composable<Routes.TargetCurrency> {
            TargetCurrencyScreen(
                navController = navController
            )
        }
    }
}