package com.currency.rateman.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.currency.rateman.R
import com.currency.rateman.data.model.CurrencyCode
import com.currency.rateman.data.model.LanguageCode
import com.currency.rateman.ui.navigation.BottomNavItem
import com.currency.rateman.data.model.ThemeMode
import com.currency.rateman.ui.components.SettingItem
import com.currency.rateman.ui.viewmodels.SettingsViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.currency.rateman.di.navigation.sharedKoinNavViewModel
import com.currency.rateman.ui.components.CurrencySettingItem

@Composable
fun SettingsScreen(
    bottomNavItems: List<BottomNavItem>,
    currentRoute: String?,
    onNavItemClick: (BottomNavItem) -> Unit,
    navController: NavHostController
) {
    val viewModel: SettingsViewModel = navController
        .currentBackStackEntry
        ?.sharedKoinNavViewModel(navController)
        ?: return

    val settings by viewModel.settings.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                bottomNavItems = bottomNavItems,
                currentRoute = currentRoute,
                onItemClick = onNavItemClick
            )
        }
    ) { paddingValues ->
        if (settings == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }  else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text(
                    text =  stringResource(id = R.string.settings),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                SettingItem(
                    label = "Interface language",
                    value = settings!!.uiLanguage.name,
                    options = enumValues<LanguageCode>().toList(),
                    onValueChange = { language ->
                        viewModel.updateLanguage(language)
                    },
                    iconRes = R.drawable.language
                )

                SettingItem(
                    label = "Theme",
                    value = settings!!.themeMode.name,
                    options = enumValues<ThemeMode>().toList(),
                    onValueChange = { theme ->
                        viewModel.updateTheme(theme)
                    },
                    modifier = Modifier.padding(top = 8.dp),
                    iconRes = R.drawable.theme
                )

                CurrencySettingItem(
                    label = "Default currency",
                    value = settings!!.defaultCurrency.name,
                    options = CurrencyCode.entries.toList(),
                    onValueChange = { currency ->
                        viewModel.updateCurrency(currency)
                    },
                    modifier = Modifier.padding(top = 8.dp),
                    iconRes = R.drawable.currency
                )
            }
        }
    }
}