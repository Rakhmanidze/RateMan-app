package com.currency.rateman.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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

@Composable
fun ProvidersList(
    providers: List<RateProvider>,
    selectedCurrency: CurrencyCode,
    modifier: Modifier = Modifier
) {
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
                text = "No results found",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            items(providers) { provider ->
                val displayRates = provider.rates.filter { rate ->
                    rate.foreignCurrency == selectedCurrency
                }
                ProviderItem(provider = provider,
                    ratesToDisplay = displayRates,
                )
            }
        }
    }
}