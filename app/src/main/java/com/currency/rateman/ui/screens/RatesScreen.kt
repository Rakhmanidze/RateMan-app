package com.currency.rateman.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.currency.rateman.data.repository.RateProviderRepositoryFake
import com.currency.rateman.ui.navigation.BottomNavItem
import com.currency.rateman.ui.viewmodels.ProvidersViewModel
import androidx.compose.runtime.getValue
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.TextFieldValue
import com.currency.rateman.data.model.ProviderType
import com.currency.rateman.ui.components.ProvidersList

@Composable
fun RatesScreen(
    bottomNavItems: List<BottomNavItem>,
    currentRoute: String?,
    onNavItemClick: (BottomNavItem) -> Unit,
    viewModel: ProvidersViewModel = ProvidersViewModel(RateProviderRepositoryFake())
) {
    val providers by viewModel.providers.collectAsState()

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var sortByExpanded by remember { mutableStateOf(false) }
    val selectedProviderType by viewModel.selectedProviderType.collectAsState()
    var isSearchFocused by remember { mutableStateOf(false) }

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
            // Fixed header section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                // Search field
                BasicTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        viewModel.updateSearchQuery(it.text)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .onFocusChanged { focusState ->
                            isSearchFocused = focusState.isFocused
                        },
                    decorationBox = { innerTextField ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (!isSearchFocused && searchQuery.text.isEmpty()) {
                                Text(
                                    text = "Find providers",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                            innerTextField()
                        }
                    },
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Filter button
                Box(modifier = Modifier.width(150.dp)) {
                    OutlinedButton(
                        onClick = { sortByExpanded = true },
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = when (selectedProviderType) {
                                ProviderType.ALL -> "All"
                                ProviderType.BANK -> "Banks"
                                ProviderType.EXCHANGE -> "Exchanges"
                                ProviderType.CRYPTO_EXCHANGE -> "Crypto Exchanges"
                            },
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    DropdownMenu(
                        expanded = sortByExpanded,
                        onDismissRequest = { sortByExpanded = false },
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .width(150.dp)
                    ) {
                        ProviderType.entries.forEach { type ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = when (type) {
                                            ProviderType.ALL -> "All"
                                            ProviderType.BANK -> "Banks"
                                            ProviderType.EXCHANGE -> "Exchanges"
                                            ProviderType.CRYPTO_EXCHANGE -> "Crypto Exchanges"
                                        },
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                },
                                onClick = {
                                    viewModel.updateProviderType(type)
                                    sortByExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Scrollable content section
            ProvidersList(providers = providers)
        }
    }
}