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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.currency.rateman.R
import com.currency.rateman.core.ui.component.BottomNavBar
import com.currency.rateman.core.ui.component.CurrencySettingItem
import com.currency.rateman.core.ui.component.LanguageSettingItem
import com.currency.rateman.core.ui.navigation.BottomNavItem
import com.currency.rateman.core.ui.viewmodel.SettingsViewModel
import com.currency.rateman.di.navigation.sharedKoinNavViewModel
import java.util.Locale

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
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.settings),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                val locale = Locale(settings!!.uiLanguage.name)
                LanguageSettingItem(
                    label = stringResource(id = R.string.app_language),
                    value = locale.getDisplayName(locale)
                        .replaceFirstChar { if (it.isLowerCase()) it.uppercase() else it.toString() },
                    iconRes = R.drawable.ic_language,
                    navController = navController
                )

                CurrencySettingItem(
                    label = stringResource(id = R.string.base_currency),
                    value = settings!!.baseCurrency.name,
                    modifier = Modifier.padding(top = 8.dp),
                    iconRes = R.drawable.ic_currency,
                    navController = navController
                )
            }
        }
    }
}