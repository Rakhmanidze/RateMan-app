package com.currency.rateman.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.currency.rateman.ui.navigation.BottomNavItem
import com.currency.rateman.ui.viewmodels.RatesViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import com.currency.rateman.data.model.RateSortType
import com.currency.rateman.di.navigation.sharedKoinNavViewModel
import com.currency.rateman.ui.components.ProvidersList
import com.currency.rateman.ui.components.SearchAndFilterHeader

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
    val selectedProviderType by viewModel.selectedProviderType.collectAsState()
    val selectedRateSortType by viewModel.selectedRateSortType.collectAsState()
    val selectedCurrency by viewModel.selectedCurrency.collectAsState()

    val isFiltersLoading = selectedCurrency == null  || selectedProviderType == null || selectedRateSortType == null

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
                    selectedProviderType = selectedProviderType,
                    onProviderTypeChange = { viewModel.updateProviderType(it) },
                    selectedCurrency = selectedCurrency,
                    onCurrencyChange = { newCurrency ->
                        viewModel.updateCurrency(newCurrency)
                    },
                    selectedRateSortType = selectedRateSortType,
                    onRateSortTypeChange = { newSortType ->
                        viewModel.updateRateSortType(newSortType)
                    }
                )
                ProvidersList(
                    providers = providers,
                    selectedCurrency = selectedCurrency
                )
            }
        }
    }
}