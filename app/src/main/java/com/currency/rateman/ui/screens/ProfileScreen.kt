package com.currency.rateman.ui.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.currency.rateman.data.model.Profile
import com.currency.rateman.ui.navigation.BottomNavItem
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen(
    bottomNavItems: List<BottomNavItem>,
    currentRoute: String?,
    profile: Profile,
    onNavItemClick: (BottomNavItem) -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavBar(
                bottomNavItems = bottomNavItems,
                currentRoute = currentRoute,
                onItemClick = onNavItemClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(text = "Settings", style = MaterialTheme.typography.headlineMedium)

            Text(
                text = "Default Currency: ${profile.defaultCurrency.name}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = "Language: ${profile.uiLanguage.name}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = "Theme: ${profile.themeMode.name}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}