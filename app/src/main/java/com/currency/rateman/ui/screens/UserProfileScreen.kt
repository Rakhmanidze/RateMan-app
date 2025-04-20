package com.currency.rateman.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.currency.rateman.R
import com.currency.rateman.data.model.UserProfile
import com.currency.rateman.data.model.playgrounds
import com.currency.rateman.ui.navigation.BottomNavItem
import com.currency.rateman.ui.theme.PlaygroundAppTheme

@Composable
fun UserProfileScreen(
    mainBottomNavItems: List<BottomNavItem>,
    currentRoute: String?,
    userProfile: UserProfile,
    startEditing: () -> Unit,
) {
    Scaffold(
        topBar = { UserProfileAppBar(startEditing) },
        bottomBar = { MainBottomNavigation(mainBottomNavItems, currentRoute) },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        UserProfileContent(
            userProfile,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileAppBar(
    startEditing: () -> Unit,
    logout: () -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(text = "User Profile")
        },
        actions = {
            IconButton(
                onClick = startEditing
            ) {
                Icon(
                    painter = painterResource(R.drawable.edit_24px),
                    contentDescription = "Edit user profile button",
                )
            }
            IconButton(
                onClick = logout
            ) {
                Icon(
                    painter = painterResource(R.drawable.logout_24px),
                    contentDescription = "Edit user profile button",
                )
            }
        },
    )
}

@Composable
fun MainBottomNavigation(
    mainBottomNavItems: List<BottomNavItem>,
    currentRoute: String?,
) {
    NavigationBar {
        mainBottomNavItems.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(item.iconId),
                        contentDescription = item.contentDescription,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(item.label) },
                selected = item.route == currentRoute,
                onClick = item.onClick,
            )
        }
    }
}

@Composable
fun UserProfileContent(
    userProfile: UserProfile,
    modifier: Modifier = Modifier
) {
    val scrollableColumnState = rememberScrollState()
    var showImages by remember { mutableStateOf(true) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollableColumnState),
    ) {
        Image (
            painter = painterResource(R.drawable.profile_placeholder),
            contentDescription = "User profile icon",
            modifier = Modifier
                .padding(top = 24.dp)
                .height(250.dp),
        )
        Text(
            text = "${userProfile.name} ${userProfile.surname}",
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = 32.dp),
        )
        Text(
            text = "${userProfile.numberOfKids} kid(s)",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp),
        )
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
                .padding(top = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Last visited playgrounds".uppercase(),
                style = MaterialTheme.typography.titleMedium,
            )
            Switch(
                checked = showImages,
                onCheckedChange = { showImages = it },
            )
        }

        for (i in 1..5) {
            PlaygroundItem(
                playgrounds[i],
                modifier = Modifier.padding(top = 16.dp),
                onClick = {},
                showImage = showImages,
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UserProfileScreenPreview() {
    PlaygroundAppTheme {
        UserProfileScreen(
            mainBottomNavItems = emptyList(),
            currentRoute = "",
            UserProfile(),
            startEditing = {},
        )
    }
}