package com.currency.rateman.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.currency.rateman.ui.navigation.BottomNavItem

@Composable
fun RatesScreen(
    bottomNavItems: List<BottomNavItem>,
    currentRoute: String?,
    onNavItemClick: (BottomNavItem) -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavBar(
                bottomNavItems = bottomNavItems,
                currentRoute = currentRoute,
                onItemClick = onNavItemClick
            )
        }
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

            Text(
                text = "Rates will be displayed here",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
