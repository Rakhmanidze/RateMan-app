package com.currency.rateman.provider.ui.screen

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
import com.currency.rateman.core.ui.navigation.BottomNavItem
import com.currency.rateman.provider.ui.viewmodel.ProviderListViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.currency.rateman.di.navigation.sharedKoinNavViewModel
import com.currency.rateman.core.ui.component.BottomNavBar
import com.currency.rateman.provider.ui.component.ProviderList
import com.currency.rateman.core.ui.component.SearchAndFilterHeader

@Composable
fun ProviderListScreen(
    bottomNavItems: List<BottomNavItem>,
    currentRoute: String?,
    onNavItemClick: (BottomNavItem) -> Unit,
    navController: NavHostController
) {
    val viewModel: ProviderListViewModel = navController
        .currentBackStackEntry
        ?.sharedKoinNavViewModel(navController)
        ?: return

    val providers by viewModel.providers.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filter by viewModel.filter.collectAsState()
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
                    targetCurrency = filter!!.targetCurrency,
                    selectedRateSortType = filter!!.selectedRateSortType,
                    onRateSortTypeChange = { newSortType ->
                        viewModel.updateRateSortType(newSortType)
                    },
                    navController = navController
                )
                ProviderList(
                    providers = providers,
                    targetCurrency = filter?.targetCurrency,
                    navController = navController
                )
            }
        }
    }
}