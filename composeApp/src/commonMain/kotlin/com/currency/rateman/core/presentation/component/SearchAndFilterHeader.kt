package com.currency.rateman.core.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.currency.rateman.core.domain.model.CurrencyCode
import com.currency.rateman.core.domain.model.RateSortType
import com.currency.rateman.core.presentation.navigation.Routes
import com.currency.rateman.provider.domain.model.ProviderType
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import rateman.composeapp.generated.resources.Res
import rateman.composeapp.generated.resources.all
import rateman.composeapp.generated.resources.banks
import rateman.composeapp.generated.resources.best_buy
import rateman.composeapp.generated.resources.best_rate
import rateman.composeapp.generated.resources.best_sell
import rateman.composeapp.generated.resources.exchanges
import rateman.composeapp.generated.resources.find_providers
import rateman.composeapp.generated.resources.loading

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
            placeholder = Res.string.find_providers,
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
                            ProviderType.ALL -> stringResource(Res.string.all)
                            ProviderType.BANK -> stringResource(Res.string.banks)
                            ProviderType.EXCHANGE -> stringResource(Res.string.exchanges)
                            null -> stringResource(Res.string.loading)
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
                                        ProviderType.ALL -> stringResource(Res.string.all)
                                        ProviderType.BANK -> stringResource(Res.string.banks)
                                        ProviderType.EXCHANGE -> stringResource(Res.string.exchanges)
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
                    onClick = { navController.navigate(Routes.TargetCurrency) },
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
                            painter = painterResource(getCurrencyIcon(targetCurrency ?: CurrencyCode.EUR)),
                            contentDescription = "${targetCurrency?.name ?: "Loading"} icon",
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = targetCurrency?.name ?: stringResource(Res.string.loading),
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
                            RateSortType.BEST_RATE -> stringResource(Res.string.best_rate)
                            RateSortType.BEST_BUY -> stringResource(Res.string.best_buy)
                            RateSortType.BEST_SELL -> stringResource(Res.string.best_sell)
                            null -> stringResource(Res.string.loading)
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
                                        RateSortType.BEST_RATE -> stringResource(Res.string.best_rate)
                                        RateSortType.BEST_BUY -> stringResource(Res.string.best_buy)
                                        RateSortType.BEST_SELL -> stringResource(Res.string.best_sell)
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