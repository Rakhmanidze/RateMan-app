package com.currency.rateman.ui.components

import com.currency.rateman.R as AppR
import android.R as AndroidR
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.currency.rateman.ui.navigation.Routes

@Composable
fun SearchAndFilterHeader(
    searchQuery: TextFieldValue,
    onSearchQueryChange: (TextFieldValue) -> Unit,
    selectedProviderType: ProviderType?,
    onProviderTypeChange: (ProviderType) -> Unit,
    selectedCurrency: CurrencyCode?,
    selectedRateSortType: RateSortType?,
    onRateSortTypeChange: (RateSortType) -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var providerTypeExpanded by remember { mutableStateOf(false) }
    var rateSortTypeExpanded by remember { mutableStateOf(false) }
    var isSearchFocused by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
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
                    Icon(
                        painter = painterResource(id = AndroidR.drawable.ic_menu_search),
                        contentDescription =  "Search icon",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 8.dp)
                    )
                    if (!isSearchFocused && searchQuery.text.isEmpty()) {
                        Text(
                            text = stringResource(id = AppR.string.find_providers),
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box {
                OutlinedButton(
                    onClick = { providerTypeExpanded = true },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier
                        .defaultMinSize(minWidth = 50.dp)
                        .wrapContentWidth(Alignment.Start),
                    enabled = selectedProviderType != null
                ) {
                    Text(
                        text = when (selectedProviderType) {
                            ProviderType.ALL -> stringResource(id = AppR.string.all)
                            ProviderType.BANK -> stringResource(id = AppR.string.banks)
                            ProviderType.EXCHANGE -> stringResource(id = AppR.string.exchanges)
                            ProviderType.CRYPTO_EXCHANGE -> stringResource(id = AppR.string.crypto_exchanges)
                            null ->  stringResource(id = AppR.string.loading)
                        },
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                DropdownMenu(
                    expanded = providerTypeExpanded && selectedProviderType != null,
                    onDismissRequest = { providerTypeExpanded = false },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .width(IntrinsicSize.Max)
                ) {
                    ProviderType.entries.forEach { type ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = when (type) {
                                        ProviderType.ALL -> stringResource(id = AppR.string.all)
                                        ProviderType.BANK -> stringResource(id = AppR.string.banks)
                                        ProviderType.EXCHANGE -> stringResource(id = AppR.string.exchanges)
                                        ProviderType.CRYPTO_EXCHANGE -> stringResource(id = AppR.string.crypto_exchanges)
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

            Box(modifier = Modifier.width(95.dp)) {
                OutlinedButton(
                    onClick = { navController.navigate(Routes.Currency.route) },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    enabled = selectedCurrency != null
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = getCurrencyIconRes(selectedCurrency ?: CurrencyCode.EUR)),
                            contentDescription = "${selectedCurrency?.name ?: "Loading"} icon",
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = selectedCurrency?.name ?: stringResource(id = AppR.string.loading),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            Box {
                OutlinedButton(
                    onClick = { rateSortTypeExpanded = true },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier
                        .defaultMinSize(minWidth = 60.dp)
                        .wrapContentWidth(),
                    enabled = selectedRateSortType != null
                ) {
                    Text(
                        text = when (selectedRateSortType) {
                            RateSortType.BEST_RATE -> stringResource(id = AppR.string.best_rate)
                            RateSortType.BEST_BUY -> stringResource(id = AppR.string.best_buy)
                            RateSortType.BEST_SELL -> stringResource(id = AppR.string.best_sell)
                            null -> stringResource(id = AppR.string.loading)
                        },
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                DropdownMenu(
                    expanded = rateSortTypeExpanded && selectedRateSortType != null,
                    onDismissRequest = { rateSortTypeExpanded = false },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .width(IntrinsicSize.Max)
                ) {
                    RateSortType.entries.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = when (option) {
                                        RateSortType.BEST_RATE -> stringResource(id = AppR.string.best_rate)
                                        RateSortType.BEST_BUY -> stringResource(id = AppR.string.best_buy)
                                        RateSortType.BEST_SELL -> stringResource(id = AppR.string.best_sell)
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