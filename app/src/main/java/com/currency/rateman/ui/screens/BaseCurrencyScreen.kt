package com.currency.rateman.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.currency.rateman.R
import com.currency.rateman.di.navigation.sharedKoinNavViewModel
import com.currency.rateman.ui.components.SearchInput
import com.currency.rateman.ui.components.getCurrencyIconRes
import com.currency.rateman.ui.viewmodels.CurrencyViewModel
import com.currency.rateman.ui.viewmodels.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun BaseCurrencyScreen(
    navController: NavHostController,
) {
    val settingsViewModel: SettingsViewModel = navController
        .currentBackStackEntry
        ?.sharedKoinNavViewModel(navController)
        ?: return

    val currencyViewModel: CurrencyViewModel = koinViewModel()
    val searchCurrency by currencyViewModel.currencySearchQuery.collectAsState()
    val filteredCurrencies by currencyViewModel.filteredCurrencies.collectAsState()
    val settings by settingsViewModel.settings.collectAsState()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { navController.popBackStack() }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.select_base_currency),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            SearchInput(
                value = searchCurrency,
                onValueChange = { currencyViewModel.updateCurrencySearchQuery(it) },
                placeholderResId = R.string.find_currencies,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(Modifier.weight(1f)) {
                if (filteredCurrencies.isEmpty()) {
                    item {
                        Text(
                            text = stringResource(R.string.no_results),
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                } else {
                    items(filteredCurrencies) { currency ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    settingsViewModel.updateCurrency(currency)
                                    navController.popBackStack()
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = getCurrencyIconRes(currency)),
                                contentDescription = "${currency.name} icon",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = currency.name,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            if (currency == settings?.baseCurrency) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_select),
                                    contentDescription = "Selected",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 4.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        )
                    }
                }
            }
        }
    }
}