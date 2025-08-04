package com.currency.rateman.ui.components

import com.currency.rateman.R as AppR
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.currency.rateman.ui.navigation.Routes
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.TextFieldValue
import com.currency.rateman.core.data.model.enums.CurrencyCode
import com.currency.rateman.core.data.model.enums.ProviderType
import com.currency.rateman.core.data.model.enums.RateSortType

@Composable
fun SearchAndFilterHeader(
    searchQuery: TextFieldValue,
    onSearchQueryChange: (TextFieldValue) -> Unit,
    selectedProviderType: ProviderType?,
    onProviderTypeChange: (ProviderType) -> Unit,
    targetCurrency: CurrencyCode?,
    selectedRateSortType: RateSortType?,
    onRateSortTypeChange: (RateSortType) -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var providerTypeExpanded by remember { mutableStateOf(false) }
    var rateSortTypeExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        SearchInput(
            value = searchQuery.text,
            onValueChange = { newText -> onSearchQueryChange(searchQuery.copy(text = newText)) },
            placeholderResId = AppR.string.find_providers,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box {
                OutlinedButton(
                    onClick = { providerTypeExpanded = true },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier
                        .wrapContentWidth(Alignment.Start),
                    enabled = selectedProviderType != null
                ) {
                    Text(
                        text = when (selectedProviderType) {
                            ProviderType.ALL -> stringResource(id = AppR.string.all)
                            ProviderType.BANK -> stringResource(id = AppR.string.banks)
                            ProviderType.EXCHANGE -> stringResource(id = AppR.string.exchanges)
                            ProviderType.CRYPTO_EXCHANGE -> stringResource(id = AppR.string.crypto_exchanges)
                            null -> stringResource(id = AppR.string.loading)
                        },
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1
                    )
                }
                DropdownMenu(
                    expanded = providerTypeExpanded && selectedProviderType != null,
                    onDismissRequest = { providerTypeExpanded = false },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
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
                                    color = MaterialTheme.colorScheme.onSurface,
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

            Box {
                OutlinedButton(
                    onClick = { navController.navigate(Routes.TargetCurrency.route) },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier
                        .width(99.dp),
                    enabled = targetCurrency != null
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = getCurrencyIconRes(targetCurrency ?: CurrencyCode.EUR)),
                            contentDescription = "${targetCurrency?.name ?: "Loading"} icon",
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = targetCurrency?.name ?: stringResource(id = AppR.string.loading),
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1
                        )
                    }
                }
            }

            Box {
                OutlinedButton(
                    onClick = { rateSortTypeExpanded = true },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier
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
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1
                    )
                }
                DropdownMenu(
                    expanded = rateSortTypeExpanded && selectedRateSortType != null,
                    onDismissRequest = { rateSortTypeExpanded = false },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
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
                                    color = MaterialTheme.colorScheme.onSurface,
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