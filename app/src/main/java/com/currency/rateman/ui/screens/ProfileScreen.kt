package com.currency.rateman.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.currency.rateman.data.model.CurrencyCode
import com.currency.rateman.data.model.LanguageCode
import com.currency.rateman.data.model.Profile
import com.currency.rateman.ui.navigation.BottomNavItem
import androidx.compose.foundation.shape.RoundedCornerShape
import com.currency.rateman.data.model.ThemeMode

@Composable
fun ProfileScreen(
    bottomNavItems: List<BottomNavItem>,
    currentRoute: String?,
    profile: Profile,
    onNavItemClick: (BottomNavItem) -> Unit,
    onProfileChange: (Profile) -> Unit
) {
    var languageExpanded by remember { mutableStateOf(false) }
    var themeExpanded by remember { mutableStateOf(false) }
    var currencyExpanded by remember { mutableStateOf(false) }

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
            // Title
            Text(
                text = "Settings",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Language Setting
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { languageExpanded = true }
                    .padding(12.dp)
            ) {
                Column {
                    Text(
                        text = "interface language",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = profile.uiLanguage.name,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                DropdownMenu(
                    expanded = languageExpanded,
                    onDismissRequest = { languageExpanded = false },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    // Assuming UILanguage is the enum type for uiLanguage
                    enumValues<LanguageCode>().forEach { language ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = language.name,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },
                            onClick = {
                                onProfileChange(profile.copy(uiLanguage = language))
                                languageExpanded = false
                            }
                        )
                    }
                }
            }

            // Theme Setting
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { themeExpanded = true }
                    .padding(12.dp)
            ) {
                Column {
                    Text(
                        text = "theme",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = profile.themeMode.name,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                DropdownMenu(
                    expanded = themeExpanded,
                    onDismissRequest = { themeExpanded = false },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    // Assuming ThemeMode is the enum type for themeMode
                    enumValues<ThemeMode>().forEach { theme ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = theme.name,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },
                            onClick = {
                                onProfileChange(profile.copy(themeMode = theme))
                                themeExpanded = false
                            }
                        )
                    }
                }
            }

            // Default Currency Setting
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { currencyExpanded = true }
                    .padding(12.dp)
            ) {
                Column {
                    Text(
                        text = "default currency",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = profile.defaultCurrency.name,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                DropdownMenu(
                    expanded = currencyExpanded,
                    onDismissRequest = { currencyExpanded = false },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    CurrencyCode.entries.forEach { currency ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = currency.name,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },
                            onClick = {
                                onProfileChange(profile.copy(defaultCurrency = currency))
                                currencyExpanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}