package com.currency.rateman.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
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
import androidx.navigation.NavHostController
import com.currency.rateman.di.navigation.sharedKoinNavViewModel
import com.currency.rateman.ui.components.ProvidersList
import com.currency.rateman.ui.components.SearchAndFilterHeader

@Composable
fun ProviderDetailsScreen(
    providerId: Int?,
    navController: NavHostController
) {
    val viewModel: RatesViewModel = navController
        .currentBackStackEntry
        ?.sharedKoinNavViewModel(navController)
        ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

    }
}