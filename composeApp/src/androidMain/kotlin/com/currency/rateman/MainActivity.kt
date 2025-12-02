package com.currency.rateman

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.currency.rateman.api.RateFetcher
import com.currency.rateman.core.ui.navigation.AppRouter
import com.currency.rateman.core.ui.theme.RateManAppTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val rateFetcher: RateFetcher by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            rateFetcher.fetchAndStoreRates()
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RateManAppTheme {
                AppRouter()
            }
        }
    }
}