package com.currency.rateman.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.currency.rateman.ui.navigation.BottomNavItem
import com.currency.rateman.ui.viewmodels.RatesViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.currency.rateman.di.navigation.sharedKoinNavViewModel
import com.currency.rateman.ui.components.BottomNavBar
import com.currency.rateman.ui.components.ProvidersList
import com.currency.rateman.ui.components.SearchAndFilterHeader
import kotlinx.coroutines.launch

@Composable
fun RatesScreen(
    bottomNavItems: List<BottomNavItem>,
    currentRoute: String?,
    onNavItemClick: (BottomNavItem) -> Unit,
    navController: NavHostController
) {
    val viewModel: RatesViewModel = navController
        .currentBackStackEntry
        ?.sharedKoinNavViewModel(navController)
        ?: return

    val providers by viewModel.providers.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filter by viewModel.filter.collectAsState()
    val apiProviders by viewModel.apiProviders.collectAsState()
    val isFiltersLoading = filter == null

    Scaffold(
        bottomBar = {
            BottomNavBar(
                bottomNavItems = bottomNavItems,
                currentRoute = currentRoute,
                onItemClick = onNavItemClick
            )
        },
        contentWindowInsets = WindowInsets.statusBars
    ) { paddingValues ->
        if (isFiltersLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                SearchAndFilterHeader(
                    searchQuery = searchQuery,
                    onSearchQueryChange = { newValue ->
                        viewModel.updateSearchQuery(newValue)
                    },
                    selectedProviderType = filter!!.selectedProviderType,
                    onProviderTypeChange = { viewModel.updateProviderType(it) },
                    selectedCurrency = filter!!.selectedCurrency,
                    selectedRateSortType = filter!!.selectedRateSortType,
                    onRateSortTypeChange = { newSortType ->
                        viewModel.updateRateSortType(newSortType)
                    },
                    navController = navController
                )
//                ProvidersList(
//                    providers = providers,
//                    selectedCurrency = filter?.selectedCurrency,
//                    navController = navController
//                )
                Button(
                    onClick = {
                        viewModel.viewModelScope.launch {
                            viewModel.getRates()
                        }
                    },
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text("Fetch Rates Manually (Debug)")
                }
                if (apiProviders.isNotEmpty()) {
                    val firstProvider = apiProviders.first()
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = "Bank: ${firstProvider.banka}")
                        Text(text = "Date: ${firstProvider.den}")
                        Text(text = "Date (Short): ${firstProvider.denc}")
                        firstProvider.kurzy["EUR"]?.let { eur ->
                            Text(text = "EUR Buy Rate: ${eur.dev_nakup ?: eur.val_nakup ?: "N/A"}")
                            Text(text = "EUR Sell Rate: ${eur.dev_prodej ?: eur.val_prodej ?: "N/A"}")
                        }
                    }
                } else {
                    Text(
                        text = "No API data available",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}