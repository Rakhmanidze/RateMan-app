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
import com.currency.rateman.data.local.UserProfile
import com.currency.rateman.ui.screens.CountdownScreen
import com.currency.rateman.ui.screens.PlaygroundEditorScreen
import com.currency.rateman.ui.screens.PlaygroundListScreen
import com.currency.rateman.ui.screens.Splashscreen
import com.currency.rateman.ui.screens.UserProfileEditorScreen
import com.currency.rateman.ui.screens.UserProfileScreen

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

    val mainBottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(
                route = Routes.PlaygroundList::class.qualifiedName.toString(),
                label = "Playgrounds",
                iconId = R.drawable.swing_icon,
                contentDescription = "Playgrounds nav bar item",
                onClick = {
                    navigateToBottomNavItem(navController, Routes.PlaygroundList)
                }
            ),
            BottomNavigationItem(
                route = Routes.UserProfile::class.qualifiedName.toString(),
                label = "User profile",
                iconId = R.drawable.person_24px,
                contentDescription = "User profile nav bar item",
                onClick = {
                    navigateToBottomNavItem(navController, Routes.UserProfile)
                }
            ),
            BottomNavigationItem(
                route = Routes.Countdown::class.qualifiedName.toString(),
                label = "Countdown",
                iconId = R.drawable.alarm_24px,
                contentDescription = "Countdown nav bar item",
                onClick = {
                    navigateToBottomNavItem(navController, Routes.Countdown)
                }
            ),
        )
    }

    var userProfile by rememberSaveable { mutableStateOf(
        UserProfile (
            name = "Ivo",
            surname = "Maly",
            numberOfKids = 3
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
                mainBottomNavigationItems,
                currentBackStackEntry.value?.destination?.route,
            )
        }
        composable<Routes.PlaygroundList>() {
            PlaygroundListScreen(
                mainBottomNavigationItems,
                currentBackStackEntry.value?.destination?.route,
                onItemClick = {
                    navController.navigate(Routes.PlaygroundEditor(it))
                }
            )
        }
        composable<Routes.UserProfile>() {
            UserProfileScreen(
                mainBottomNavigationItems,
                currentBackStackEntry.value?.destination?.route,
                userProfile,
                {
                    navController.navigate(Routes.UserProfileEditor(
                        userProfile.name,
                        userProfile.surname,
                        userProfile.numberOfKids
                    ))
                }
            )
        }
        composable<Routes.UserProfileEditor>() {
            UserProfileEditorScreen(
                saveUserProfile = {
                    userProfile = it
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