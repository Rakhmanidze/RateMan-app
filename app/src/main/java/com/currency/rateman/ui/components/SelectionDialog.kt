package com.currency.rateman.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import com.currency.rateman.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun<T> SelectionDialog(
    title: String,
    options: List<T>,
    selectedOption: T?,
    onOptionSelected: (T) -> Unit,
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
            options.forEach { option ->
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