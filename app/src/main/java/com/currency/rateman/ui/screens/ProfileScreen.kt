package com.currency.rateman.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.currency.rateman.data.model.CurrencyCode
import com.currency.rateman.data.model.LanguageCode
import com.currency.rateman.data.model.Profile
import com.currency.rateman.ui.navigation.BottomNavItem
import com.currency.rateman.data.model.ThemeMode
import com.currency.rateman.ui.components.SettingItem

@Composable
fun ProfileScreen(
    bottomNavItems: List<BottomNavItem>,
    currentRoute: String?,
    profile: Profile,
    onNavItemClick: (BottomNavItem) -> Unit,
    onProfileChange: (Profile) -> Unit
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
            Text(
                text = "Settings",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            SettingItem(
                label = "Interface language",
                value = profile.uiLanguage.name,
                options = enumValues<LanguageCode>().toList(),
                onValueChange = { language ->
                    onProfileChange(profile.copy(uiLanguage = language))
                }
            )

            SettingItem(
                label = "Theme",
                value = profile.themeMode.name,
                options = enumValues<ThemeMode>().toList(),
                onValueChange = { theme ->
                    onProfileChange(profile.copy(themeMode = theme))
                },
                modifier = Modifier.padding(top = 8.dp)
            )

            SettingItem(
                label = "Default currency",
                value = profile.defaultCurrency.name,
                options = CurrencyCode.entries.toList(),
                onValueChange = { currency ->
                    onProfileChange(profile.copy(defaultCurrency = currency))
                },
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}