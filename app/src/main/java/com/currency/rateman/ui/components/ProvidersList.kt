package com.currency.rateman.ui.components

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
import com.currency.rateman.data.model.CurrencyCode
import com.currency.rateman.data.model.RateProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*

@Composable
fun ProvidersList(
    providers: List<RateProvider>,
    selectedCurrency: CurrencyCode?,
    modifier: Modifier = Modifier
) {
    var selectedProvider by remember { mutableStateOf<RateProvider?>(null) }

    if (providers.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(40.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "No results",
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
                    text = "Buy / Sell",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                items(providers) { provider ->
                    val displayRates = provider.rates.filter { rate ->
                        rate.foreignCurrency == selectedCurrency
                    }
                    ProviderItem(
                        provider = provider,
                        ratesToDisplay = displayRates,
                        onClick = { selectedProvider = provider }
                    )
                }
            }
        }
    }
}