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
import com.currency.rateman.core.ui.navigation.BottomNavItem
import com.currency.rateman.core.ui.navigation.Routes

@Composable
fun BottomNavBar(
    bottomNavItems: List<BottomNavItem>,
    currentRoute: String?,
    onItemClick: (BottomNavItem) -> Unit
) {
    NavigationBar(
        modifier = Modifier.height(56.dp),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        bottomNavItems.forEach { item ->
            val isSelected = when (item.route) {
                Routes.Rates -> currentRoute?.contains("Rates") == true
                Routes.Settings -> currentRoute?.contains("Settings") == true
                else -> false
            }

            NavigationBarItem(
                modifier = Modifier.padding(top = 10.dp),
                selected = isSelected,
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