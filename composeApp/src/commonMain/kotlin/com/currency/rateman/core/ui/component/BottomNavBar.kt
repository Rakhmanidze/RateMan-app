package com.currency.rateman.core.ui.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.currency.rateman.core.ui.navigation.BottomNavItem

@Composable
fun BottomNavBar(
    bottomNavItems: List<BottomNavItem>,
    navController: NavHostController,
    onItemClick: (BottomNavItem) -> Unit
) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState().value

    NavigationBar(
        modifier = Modifier.height(56.dp),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        bottomNavItems.forEach { item ->
            val selected = currentBackStackEntry?.destination?.route == item.route.toString() ||
                    currentBackStackEntry?.destination?.parent?.route == item.route.toString()

            NavigationBarItem(
                modifier = Modifier.padding(top = 10.dp),
                selected = selected,
                onClick = { onItemClick(item) },
                icon = {
                    Icon(
                        painter = item.icon(),
                        contentDescription = item.contentDescription,
                        modifier = Modifier.size(24.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onSurface,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                    indicatorColor = MaterialTheme.colorScheme.background
                )
            )
        }
    }
}