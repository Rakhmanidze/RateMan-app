package com.currency.rateman.core.presentation.screen

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.currency.rateman.core.presentation.component.SearchInput
import com.currency.rateman.core.presentation.component.getCurrencyIcon
import com.currency.rateman.core.presentation.viewmodel.CurrencyViewModel
import com.currency.rateman.provider.ui.viewmodel.ProviderListViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import rateman.composeapp.generated.resources.Res
import rateman.composeapp.generated.resources.find_currencies
import rateman.composeapp.generated.resources.ic_select
import rateman.composeapp.generated.resources.no_results
import rateman.composeapp.generated.resources.select_target_currency

@Composable
fun TargetCurrencyScreen(
    navController: NavHostController
) {
    val providerListViewModel = koinViewModel<ProviderListViewModel>()
    val currencyViewModel = koinViewModel<CurrencyViewModel>()

    val searchCurrency by currencyViewModel.currencySearchQuery.collectAsState()
    val filteredCurrencies by currencyViewModel.filteredCurrencies.collectAsState()
    val filter by providerListViewModel.filter.collectAsState()

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
                    text = stringResource(Res.string.select_target_currency),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            SearchInput(
                value = searchCurrency,
                onValueChange = { currencyViewModel.updateCurrencySearchQuery(it) },
                placeholder = Res.string.find_currencies,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(Modifier.weight(1f)) {
                if (filteredCurrencies.isEmpty()) {
                    item {
                        Text(
                            text = stringResource(Res.string.no_results),
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
                                    providerListViewModel.updateCurrency(currency)
                                    navController.popBackStack()
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(getCurrencyIcon(currency)),
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
                            if (currency == filter?.targetCurrency) {
                                Icon(
                                    painter = painterResource(Res.drawable.ic_select),
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