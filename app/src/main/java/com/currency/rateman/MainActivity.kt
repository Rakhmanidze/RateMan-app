package com.currency.rateman

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.currency.rateman.ui.navigation.AppRouter
import com.currency.rateman.ui.theme.RateManAppTheme
import com.currency.rateman.utils.LanguageManager
import com.currency.rateman.utils.ThemeManager
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            ThemeManager.initTheme(this@MainActivity)
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
        lifecycleScope.launch {
            ThemeManager.initTheme(this@MainActivity)
        }
    }

    override fun attachBaseContext(newBase: Context) {
        val updatedContext = LanguageManager.wrapContextWithSavedLanguage(newBase)
        super.attachBaseContext(updatedContext)
    }
}
//TODO: remove 0 after . in Provider screen and in rates screen
//TODO: rename Exchange VIP to SMĚNÁRNA Štefánikova