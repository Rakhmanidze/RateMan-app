package com.currency.rateman.ui.screens

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.currency.rateman.ui.navigation.BottomNavItem

@Composable
fun BottomNavBar(
    bottomNavItems: List<BottomNavItem>,
    currentRoute: String?,
    onItemClick: (BottomNavItem) -> Unit
) {
    NavigationBar(
        modifier = Modifier.height(56.dp)
    ) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                modifier = Modifier.padding(top = 10.dp),
                selected = currentRoute == item.route,
                onClick = { onItemClick(item) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = item.contentDescription,
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
        }
    }
}