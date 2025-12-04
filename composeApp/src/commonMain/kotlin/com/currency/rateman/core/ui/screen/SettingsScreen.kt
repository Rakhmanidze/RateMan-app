package com.currency.rateman.core.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.currency.rateman.core.ui.component.BottomNavBar
import com.currency.rateman.core.ui.component.CurrencySettingItem
import com.currency.rateman.core.ui.navigation.BottomNavItem
import com.currency.rateman.core.ui.viewmodel.SettingsViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import rateman.composeapp.generated.resources.Res
import rateman.composeapp.generated.resources.base_currency
import rateman.composeapp.generated.resources.ic_currency
import rateman.composeapp.generated.resources.settings

@Composable
fun SettingsScreen(
    bottomNavItems: List<BottomNavItem>,
    currentRoute: String?,
    onNavItemClick: (BottomNavItem) -> Unit,
    navController: NavHostController
) {
    val settingsViewModel = koinViewModel<SettingsViewModel>()
    val settings by settingsViewModel.settings.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                bottomNavItems = bottomNavItems,
                navController = navController,
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
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = stringResource(Res.string.settings),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                CurrencySettingItem(
                    label = stringResource(Res.string.base_currency),
                    value = settings!!.baseCurrency.name,
                    modifier = Modifier.padding(top = 8.dp),
                    iconRes = Res.drawable.ic_currency,
                    navController = navController
                )
            }
        }
    }
}