package com.currency.rateman.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import com.currency.rateman.R
import com.currency.rateman.data.model.CurrencyCode
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencySelectionDialog(
    title: String,
    options: List<CurrencyCode>,
    selectedOption: CurrencyCode?,
    onOptionSelected: (CurrencyCode) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxWidth(),
        sheetState = rememberModalBottomSheetState(),
        windowInsets = WindowInsets(0),
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            var searchCurrency by remember { mutableStateOf("") }
            TextField(
                value = searchCurrency,
                onValueChange = { searchCurrency = it },
                placeholder = { Text("Search currencies")},
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 9.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            val filteredOptions = options.filter {
                it.name.contains(searchCurrency, ignoreCase = true)
            }

            if (filteredOptions.isEmpty()) {
                Text(
                    text = "No currencies found",
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            filteredOptions.forEach { option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onOptionSelected(option)
                            onDismiss()
                        }
                        .padding(vertical = 9.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = getCurrencyIconRes(option)),
                        contentDescription = "${option.name} icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = option.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                    if (option == selectedOption) {
                        Icon(
                            painter = painterResource(id = R.drawable.select),
                            contentDescription = "Selected",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Divider()
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}