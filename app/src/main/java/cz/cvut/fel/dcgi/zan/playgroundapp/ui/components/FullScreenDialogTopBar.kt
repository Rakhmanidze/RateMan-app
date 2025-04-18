package cz.cvut.fel.dcgi.zan.playgroundapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenDialogTopBar(
    title: String,
    cancelEditing: () -> Unit,
    saveUserProfile: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(
                onClick = cancelEditing
            ) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "Navigation back",
                )
            }
        },
        actions = {
            IconButton(
                onClick = saveUserProfile
            ) {
                Icon(
                    Icons.Filled.Done,
                    contentDescription = "Content",
                )
            }
        },
    )
}