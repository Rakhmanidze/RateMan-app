package cz.cvut.fel.dcgi.zan.playgroundapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cz.cvut.fel.dcgi.zan.playgroundapp.R
import cz.cvut.fel.dcgi.zan.playgroundapp.data.local.Playground
import cz.cvut.fel.dcgi.zan.playgroundapp.data.local.UserProfile
import cz.cvut.fel.dcgi.zan.playgroundapp.ui.components.FullScreenDialogTopBar
import cz.cvut.fel.dcgi.zan.playgroundapp.ui.theme.PlaygroundAppTheme
import cz.cvut.fel.dcgi.zan.playgroundapp.ui.viewmodels.PlaygroundEditorEvent
import cz.cvut.fel.dcgi.zan.playgroundapp.ui.viewmodels.PlaygroundEditorViewModel
import cz.cvut.fel.dcgi.zan.playgroundapp.ui.viewmodels.UserProfileEditorEvent
import cz.cvut.fel.dcgi.zan.playgroundapp.ui.viewmodels.UserProfileEditorViewModel
import cz.cvut.fel.dcgi.zan.playgroundapp.ui.viewmodels.UserProfileUiState

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