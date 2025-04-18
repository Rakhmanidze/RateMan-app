package com.currency.rateman.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.currency.rateman.ui.navigation.BottomNavigationItem
import com.currency.rateman.ui.viewmodels.CountdownScreenViewModel

@Composable
fun CountdownScreen(
    mainBottomNavigationItems: List<BottomNavigationItem>,
    currentDestination: String?,
) {
    Scaffold(
        bottomBar = {
            MainBottomNavigation(mainBottomNavigationItems, currentDestination)
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        CountdownContent(
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun CountdownContent(
    modifier: Modifier = Modifier,
    viewModel: CountdownScreenViewModel = viewModel()
) {
    val isEditing by viewModel.isEditing.collectAsStateWithLifecycle()
    val remainingTime by viewModel.remainingTime.collectAsStateWithLifecycle()
    val isRunning by viewModel.isRunning.collectAsStateWithLifecycle()

    if (isEditing) {
        CountdownForm(
            countdownValue = remainingTime,
            onStartCountdown = { viewModel.startCountdown(it) },
            modifier = modifier
        )
    } else {
        CountdownTimer(
            remainingTime = remainingTime,
            isRunning = isRunning,
            onStop = { viewModel.startEditing() },
            onPause = { viewModel.onPause() },
            onResume = { viewModel.onResume() },
            onReset = { viewModel.onReset() },
            modifier = modifier
        )
    }
}

@Composable
fun CountdownForm (
    countdownValue: Int,
    onStartCountdown: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var inputValue by rememberSaveable { mutableStateOf(countdownValue.toString()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = inputValue,
            onValueChange = { inputValue = it },
            label = { Text("Zadej hodnotu odpočtu") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onStartCountdown(inputValue.toIntOrNull() ?: 0)} ) {
            Text("Start")
        }
    }
}

@Composable
fun CountdownTimer(
    remainingTime: Int,
    isRunning: Boolean,
    onStop: () -> Unit,
    onPause: () -> Unit,
    onResume: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Hodnota odpočtu",
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
        )
        Text(
            "$remainingTime",
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(16.dp))
        when {
            remainingTime == 0 -> {
                Spacer(modifier = Modifier.height(48.dp))
            }
            isRunning -> {
                Button(onClick = onPause) {
                    Text("Pauza")
                }
            }
            else -> {
                Button(onClick = onResume) {
                    Text("Pokračovat")
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onReset) {
            Text("Reset")
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onStop) {
            Text("Stop")
        }
    }
}