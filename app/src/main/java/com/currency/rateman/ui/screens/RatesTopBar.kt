package com.currency.rateman.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.currency.rateman.data.model.ProviderType
import com.currency.rateman.ui.viewmodels.ProvidersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatesTopAppBar(viewModel: ProvidersViewModel) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var sortByExpanded by remember { mutableStateOf(false) }
    val selectedProviderType by viewModel.selectedProviderType.collectAsState()

    TopAppBar(
        title = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                // Search Input
                BasicTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        viewModel.updateSearchQuery(it.text)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFF2D2D2D),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    decorationBox = { innerTextField ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (searchQuery.text.isEmpty()) {
                                Text(
                                    text = "Find providers",
                                    color = Color(0xFFE0E0E0).copy(alpha = 0.5f),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                            innerTextField()
                        }
                    },
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        color = Color(0xFFE0E0E0)
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))


            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF1A1A1A)
        )
    )
}