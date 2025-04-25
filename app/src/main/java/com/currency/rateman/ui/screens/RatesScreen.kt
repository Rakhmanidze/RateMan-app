package com.currency.rateman.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.currency.rateman.data.repository.RateProviderRepositoryFake
import com.currency.rateman.ui.navigation.BottomNavItem
import com.currency.rateman.ui.viewmodels.ProvidersViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import com.currency.rateman.data.model.RateSortType
import com.currency.rateman.ui.components.ProvidersList
import com.currency.rateman.ui.components.SearchAndFilterHeader

@Composable
fun RatesScreen(
    bottomNavItems: List<BottomNavItem>,
    currentRoute: String?,
    onNavItemClick: (BottomNavItem) -> Unit,
    viewModel: ProvidersViewModel = ProvidersViewModel(RateProviderRepositoryFake())
) {
    val providers by viewModel.providers.collectAsState()
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val selectedProviderType by viewModel.selectedProviderType.collectAsState()
    var selectedRateSortType by remember { mutableStateOf(RateSortType.BEST_RATE) }
    val selectedCurrency by viewModel.selectedCurrency.collectAsState()

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            SearchAndFilterHeader(
                searchQuery = searchQuery,
                onSearchQueryChange = {
                    searchQuery = it
                    viewModel.updateSearchQuery(it.text)
                },
                selectedProviderType = selectedProviderType,
                onProviderTypeChange = { viewModel.updateProviderType(it) },
                selectedCurrency = selectedCurrency,
                onCurrencyChange ={ newCurrency ->
                    viewModel.updateCurrency(newCurrency)
                },
                selectedRateSortType = selectedRateSortType,
                onRateSortTypeChange = { newSortType ->
                    selectedRateSortType = newSortType
                    viewModel.updateRateSortType(newSortType)
                }
            )
            ProvidersList(providers = providers,
                selectedCurrency = selectedCurrency
            )
        }
    }
}