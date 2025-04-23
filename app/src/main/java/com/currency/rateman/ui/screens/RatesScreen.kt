package com.currency.rateman.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.currency.rateman.data.repository.RateProviderRepositoryFake
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import com.currency.rateman.ui.navigation.BottomNavItem
import com.currency.rateman.ui.viewmodels.ProvidersViewModel
import androidx.compose.runtime.getValue
import com.currency.rateman.data.model.RateProvider

@Composable
fun RatesScreen(
    bottomNavItems: List<BottomNavItem>,
    currentRoute: String?,
    onNavItemClick: (BottomNavItem) -> Unit,
    viewModel: ProvidersViewModel = ProvidersViewModel(RateProviderRepositoryFake())
) {
    val providers by viewModel.providers.collectAsState()

    Scaffold(
        topBar = {
            RatesTopAppBar(viewModel = viewModel)
        },

        bottomBar = {
            BottomNavBar(
                bottomNavItems = bottomNavItems,
                currentRoute = currentRoute,
                onItemClick = onNavItemClick
            )
        },
        contentWindowInsets = WindowInsets.statusBars
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Currency Rates",
                style = MaterialTheme.typography.headlineMedium
            )

            if (providers.isEmpty()) {
                Text(
                    text = "No providers available",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    items(providers) { provider ->
                        ProviderItem(provider = provider)
                    }
                }
            }
        }
    }
}


@Composable
fun ProviderItem(provider: RateProvider) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = provider.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = provider.phoneNumber,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            provider.rates.forEach { rate ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = rate.foreignCurrency.name,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Buy: ${rate.buyRate} | Sell: ${rate.sellRate}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}