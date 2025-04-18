package com.currency.rateman

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.currency.rateman.ui.navigation.AppRouter
import com.currency.rateman.ui.theme.PlaygroundAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlaygroundAppTheme {
                AppRouter()
            }
        }
    }
}
