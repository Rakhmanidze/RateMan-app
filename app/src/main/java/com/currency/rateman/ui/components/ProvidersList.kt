package com.currency.rateman.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.currency.rateman.data.model.RateProvider

@Composable
fun ProvidersList(
    providers: List<RateProvider>,
    modifier: Modifier = Modifier
) {
    if (providers.isEmpty()) {
        Text(
            text = "No providers available",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            items(providers) { provider ->
                ProviderItem(provider = provider)
            }
        }
    }
}