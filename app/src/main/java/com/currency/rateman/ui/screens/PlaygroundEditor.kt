package com.currency.rateman.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.currency.rateman.R
import com.currency.rateman.data.local.Playground
import com.currency.rateman.ui.components.FullScreenDialogTopBar
import com.currency.rateman.ui.theme.PlaygroundAppTheme
import com.currency.rateman.ui.viewmodels.PlaygroundEditorEvent
import com.currency.rateman.ui.viewmodels.PlaygroundEditorViewModel

@Composable
fun PlaygroundEditorScreen(
    savePlayground: (Playground) -> Unit,
    cancelEditing: () -> Unit,
    viewModel: PlaygroundEditorViewModel = viewModel(),
) {
    val playground by viewModel.playground.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { FullScreenDialogTopBar (
            title = "Edit Playground",
            cancelEditing = cancelEditing,
            saveUserProfile = {
                savePlayground(
                    Playground(
                    )
                )
            }
        ) },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        PlaygroundEditorContent(
            playground,
            { viewModel.onEvent(it)},
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun PlaygroundEditorContent(
    playground: Playground,
    onScreenEvent: (PlaygroundEditorEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val textFieldModifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxWidth()
    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        UserProfileEditorTextField(
            value = playground.name,
            onValueChange = { onScreenEvent(PlaygroundEditorEvent.NameChanged(it)) },
            label = "Name",
            icon = ImageVector.vectorResource(R.drawable.cancel_24px),
            modifier = textFieldModifier,
        )
        UserProfileEditorTextField(
            value = playground.address,
            onValueChange = { onScreenEvent(PlaygroundEditorEvent.AddressChanged(it)) },
            label = "Address",
            icon = ImageVector.vectorResource(R.drawable.cancel_24px),
            modifier = textFieldModifier,
        )
        UserProfileEditorTextField(
            value = playground.imageUrl,
            onValueChange = { onScreenEvent(PlaygroundEditorEvent.ImageUrlChanged(it)) },
            label = "Image URL",
            icon = ImageVector.vectorResource(R.drawable.cancel_24px),
            modifier = textFieldModifier,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlaygroundEditorScreenPreview() {
    PlaygroundAppTheme {
        PlaygroundEditorScreen(
            savePlayground = {},
            cancelEditing = {},
        )
    }
}