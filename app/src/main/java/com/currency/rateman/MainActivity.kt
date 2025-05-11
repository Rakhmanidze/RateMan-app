package com.currency.rateman

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.currency.rateman.ui.navigation.AppRouter
import com.currency.rateman.ui.theme.RateManAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Apply saved language on startup
        lifecycleScope.launch {
            LanguageManager.applySavedLanguage(this@MainActivity)
        }

        enableEdgeToEdge()
        setContent {
            RateManAppTheme {
                AppRouter()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            LanguageManager.applySavedLanguage(this@MainActivity)
        }
    }
}