package com.currency.rateman.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
import com.currency.rateman.data.model.CurrencyCode
import com.currency.rateman.data.model.ProviderType
import com.currency.rateman.data.model.RateSortType

@Composable
fun SearchAndFilterHeader(
    searchQuery: TextFieldValue,
    onSearchQueryChange: (TextFieldValue) -> Unit,
    selectedProviderType: ProviderType,
    onProviderTypeChange: (ProviderType) -> Unit,
    selectedCurrency: CurrencyCode,
    onCurrencyChange: (CurrencyCode) -> Unit,
    selectedRateSortType: RateSortType,
    onRateSortTypeChange: (RateSortType) -> Unit,
    modifier: Modifier = Modifier
) {
    var providerTypeExpanded by remember { mutableStateOf(false) }
    var currencyExpanded by remember { mutableStateOf(false) }
    var rateSortTypeExpanded by remember { mutableStateOf(false) }
    var isSearchFocused by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        // Search field
        BasicTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
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

        // Filter buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(modifier = Modifier.width(150.dp)) {
                OutlinedButton(
                    onClick = { providerTypeExpanded = true },
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
                    expanded = providerTypeExpanded,
                    onDismissRequest = { providerTypeExpanded = false },
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
                                onProviderTypeChange(type)
                                providerTypeExpanded = false
                            }
                        )
                    }
                }
            }

            Box(modifier = Modifier.width(80.dp)) {
                OutlinedButton(
                    onClick = { currencyExpanded = true },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = selectedCurrency.name,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                DropdownMenu(
                    expanded = currencyExpanded,
                    onDismissRequest = { currencyExpanded = false },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .width(80.dp)
                ) {
                    CurrencyCode.entries.forEach { currency ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = currency.name,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            },
                            onClick = {
                                onCurrencyChange(currency)
                                currencyExpanded = false
                            }
                        )
                    }
                }
            }

            Box(modifier = Modifier.width(102.dp)) {
                OutlinedButton(
                    onClick = { rateSortTypeExpanded = true },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = when (selectedRateSortType) {
                            RateSortType.BEST_RATE -> "Best Rate"
                            RateSortType.BEST_BUY -> "Best Buy"
                            RateSortType.BEST_SELL -> "Best Sell"
                        },
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                DropdownMenu(
                    expanded = rateSortTypeExpanded,
                    onDismissRequest = { rateSortTypeExpanded = false },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .width(102.dp)
                ) {
                    RateSortType.entries.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = when (option) {
                                        RateSortType.BEST_RATE -> "Best Rate"
                                        RateSortType.BEST_BUY -> "Best Buy"
                                        RateSortType.BEST_SELL -> "Best Sell"
                                    },
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            },
                            onClick = {
                                onRateSortTypeChange(option)
                                rateSortTypeExpanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}