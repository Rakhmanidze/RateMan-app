package com.currency.rateman.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.currency.rateman.data.local.Playground
import com.currency.rateman.ui.components.SingleLineText
import com.currency.rateman.ui.navigation.BottomNavigationItem
import com.currency.rateman.ui.viewmodels.PlaygroundListScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaygroundListScreen(
    mainBottomNavigationItems: List<BottomNavigationItem>,
    currentDestination: String?,
    onItemClick: (Long) -> Unit,
    viewModel: PlaygroundListScreenViewModel = viewModel()
) {
    val playgroundList by viewModel.playgroundList.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

//    LaunchedEffect(Unit) {
//        viewModel.snackbarEvent.collectLatest { message ->
//            if (message.isNotBlank()) {
//                snackbarHostState.showSnackbar(message)
//            }
//        }
//    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text (text = "Playgrounds") },
                actions = {
                    IconButton(
                        onClick = { viewModel.deleteAllPlaygrounds() }
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete all")
                    }
                    IconButton(
                        onClick = { viewModel.downloadPlaygrounds() }
                    ) {
                        Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "Download")
                    }
                }
            )
        },
        bottomBar = {
            MainBottomNavigation(mainBottomNavigationItems, currentDestination)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {}
            ) {
                Icon(Icons.Filled.Add, "Add new playground")
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        PlaygroundListContent(
            playgroundList = playgroundList,
            onClick = {
                onItemClick(it)
            },
            modifier = Modifier.padding(innerPadding)
        )
//        CircularProgressIndicator(
//            modifier = Modifier
//                .padding(innerPadding)
//        )
    }
}

@Composable
fun PlaygroundListContent(
    playgroundList: List<Playground>,
    onClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {

    LazyColumn (
        modifier = modifier
    ) {
        items(playgroundList) { item ->
            PlaygroundItem(
                item = item,
                onClick = onClick,
                showImage = true,
            )
        }
    }
}

@Composable
fun PlaygroundItem(
    item: Playground,
    showImage : Boolean,
    onClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row (
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .clickable {
                onClick(item.id)
            }
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showImage) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.name,
                modifier = Modifier.width(80.dp),
            )
        }
        Column (
            modifier = Modifier.weight(1f)
        ) {
            SingleLineText(
                text = item.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 16.dp),
            )
            SingleLineText(
                text = item.address,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp),
            )
            SingleLineText(
                text = "${item.lat} ${item.lon}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp),
            )
            HorizontalDivider(
                modifier = Modifier.padding(top = 8.dp, start = 16.dp),
            )
        }
    }
}
