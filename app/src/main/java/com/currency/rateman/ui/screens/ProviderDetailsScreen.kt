package com.currency.rateman.ui.screens

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import com.currency.rateman.di.navigation.sharedKoinNavViewModel
import com.currency.rateman.ui.viewmodels.ProviderDetailsViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.currency.rateman.R

@Composable
fun ProviderDetailsScreen(
    providerId: Long?,
    navController: NavHostController
) {
    val viewModel: ProviderDetailsViewModel = navController
        .currentBackStackEntry
        ?.sharedKoinNavViewModel(navController)
        ?: return
    val provider by viewModel.provider.collectAsState()

    providerId?.let { id ->
        viewModel.getProviderById(id)
    } ?: run {
        navController.popBackStack()
        return
    }

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
                    modifier = Modifier.size(32.dp)
                        .clickable { navController.popBackStack() }
                )
                Text(
                    text = provider?.name ?: (stringResource(id = R.string.loading) + "..."),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}