//package com.currency.rateman.ui.screens
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Close
//import androidx.compose.material.icons.filled.Done
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.vectorResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.currency.rateman.R
//import com.currency.rateman.data.model.Profile
//import com.currency.rateman.ui.theme.PlaygroundAppTheme
//import com.currency.rateman.ui.viewmodels.UserProfileEditorEvent
//import com.currency.rateman.ui.viewmodels.UserProfileEditorViewModel
//import com.currency.rateman.ui.viewmodels.UserProfileUiState
//
//@Composable
//fun UserProfileEditorScreen(
//    //userProfile: UserProfile,
//    saveUserProfile: (Profile) -> Unit,
//    cancelEditing: () -> Unit,
//    viewModel: UserProfileEditorViewModel = viewModel(),
//) {
//    //var profileName by rememberSaveable { mutableStateOf(userProfile.name) }
////    var profileSurname by rememberSaveable { mutableStateOf(userProfile.surname) }
////    var profileNumberOfKids by rememberSaveable { mutableStateOf(userProfile.numberOfKids.toString()) }
//
//    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
////    val userProfile by viewModel.userProfile
//
//    Scaffold(
//        topBar = { UserProfileEditorAppBar(
//            cancelEditing = cancelEditing,
//            saveUserProfile = {
//                saveUserProfile(
//                    Profile(
//                        name = userProfile.name,
//                        surname = userProfile.surname,
//                        numberOfKids = userProfile.noOfKids.toIntOrNull() ?: 0
//                    )
//                )
//            }
//        ) },
//        modifier = Modifier.fillMaxSize(),
//    ) { innerPadding ->
//        UserProfileEditorContent(
//            userProfile,
//            {event -> viewModel.onEvent(event)},
//            modifier = Modifier.padding(innerPadding)
//        )
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun UserProfileEditorAppBar(
//    cancelEditing: () -> Unit,
//    saveUserProfile: () -> Unit,
//) {
//    TopAppBar(
//        title = {
//            Text(text = "Edit Profile")
//        },
//        navigationIcon = {
//            IconButton(
//                onClick = cancelEditing
//            ) {
//                Icon(
//                    Icons.Filled.Close,
//                    contentDescription = "Navigation back",
//                )
//            }
//        },
//        actions = {
//            IconButton(
//                onClick = saveUserProfile
//            ) {
//                Icon(
//                    Icons.Filled.Done,
//                    contentDescription = "Save user profile",
//                )
//            }
//        },
//    )
//}
//
//@Composable
//fun UserProfileEditorContent(
//    userProfile: UserProfileUiState,
//    onScreenEvent: (UserProfileEditorEvent) -> Unit,
//    modifier: Modifier = Modifier,
//) {
//    val textFieldModifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxWidth()
//    val scrollState = rememberScrollState()
//
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = modifier
//            .fillMaxSize()
//            .verticalScroll(scrollState),
//    ) {
//        Image (
//            painter = painterResource(R.drawable.profile_placeholder),
//            contentDescription = "User profile icon",
//            modifier = Modifier.padding(top = 24.dp).height(250.dp),
//        )
//        UserProfileEditorTextField(
//            value = userProfile.name,
//            onValueChange = { onScreenEvent(UserProfileEditorEvent.NameChanged(it)) },
//            label = "Name",
//            icon = ImageVector.vectorResource(R.drawable.cancel_24px),
//            modifier = textFieldModifier,
//        )
//        UserProfileEditorTextField(
//            value = userProfile.surname,
//            onValueChange = { onScreenEvent(UserProfileEditorEvent.SurnameChanged(it)) },
//            label = "Surname",
//            icon = ImageVector.vectorResource(R.drawable.cancel_24px),
//            modifier = textFieldModifier,
//        )
//        UserProfileEditorTextField(
//            value = userProfile.noOfKids,
//            onValueChange = { onScreenEvent(UserProfileEditorEvent.NoOfKidsChanged(it)) },
//            label = "Numebr of kids",
//            icon = ImageVector.vectorResource(R.drawable.cancel_24px),
//            modifier = textFieldModifier,
//        )
//    }
//}
//
//@Composable
//fun UserProfileEditorTextField(
//    value: String,
//    onValueChange: (String) -> Unit,
//    icon: ImageVector,
//    modifier: Modifier = Modifier,
//    onTrailingIconClick: () -> Unit = { onValueChange("") },
//    label: String = "",
//) {
//    OutlinedTextField(
//        value = value,
//        onValueChange = onValueChange,
//        label = { Text(label) },
//        modifier = modifier,
//        trailingIcon = {
//            IconButton(
//                onClick = onTrailingIconClick
//            ) {
//                Icon(
//                    icon,
//                    contentDescription = "Clear text",
//                )
//            }
//        },
//    )
//}
//
//
//@Preview(showBackground = true)
//@Composable
//fun UserProfileEditorScreenPreview() {
//    PlaygroundAppTheme {
//        UserProfileEditorScreen(saveUserProfile = {}, cancelEditing = {})
//    }
//}