package com.currency.rateman

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.currency.rateman.api.RateFetcher
import com.currency.rateman.core.ui.navigation.AppRouter
import com.currency.rateman.core.ui.theme.RateManAppTheme
import com.currency.rateman.core.utils.LanguageManager
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val rateFetcher: RateFetcher by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
//            ThemeManager.initTheme(this@MainActivity)
            rateFetcher.fetchAndStoreRates()
        }
        LanguageManager.initLanguage(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RateManAppTheme {
                AppRouter()
            }
        }
    }

    override fun onConfigurationChanged(newConfig: android.content.res.Configuration) {
        super.onConfigurationChanged(newConfig)
        LanguageManager.initLanguage(this)
//        lifecycleScope.launch {
//            ThemeManager.initTheme(this@MainActivity)
//        }
    }

    override fun attachBaseContext(newBase: Context) {
        val updatedContext = LanguageManager.wrapContextWithSavedLanguage(newBase)
        super.attachBaseContext(updatedContext)
    }
}