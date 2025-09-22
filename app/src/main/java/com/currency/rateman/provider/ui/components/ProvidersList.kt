package com.currency.rateman.provider.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.currency.rateman.core.data.model.CurrencyCode
import com.currency.rateman.provider.data.model.Provider
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.currency.rateman.R

@Composable
fun ProvidersList(
    providers: List<Provider>,
    targetCurrency: CurrencyCode?,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    if (providers.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(40.dp)
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = stringResource(id = R.string.no_results),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "${stringResource(id = R.string.buy)} / ${stringResource(id = R.string.sell)}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                items(providers) { provider ->
                    val displayRates = provider.rates.filter { rate ->
                        rate.foreignCurrency == targetCurrency
                    }
                    ProviderItem(
                        provider = provider,
                        ratesToDisplay = displayRates,
                        onClick = { id -> navController.navigate("providerDetail/$id") }
                    )
                }
            }
        }
    }
}