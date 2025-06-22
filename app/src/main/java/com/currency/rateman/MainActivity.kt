package com.currency.rateman

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.currency.rateman.ui.navigation.AppRouter
import com.currency.rateman.ui.theme.RateManAppTheme
import com.currency.rateman.utils.LanguageManager

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
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
    }

    override fun attachBaseContext(newBase: Context) {
        val updatedContext = LanguageManager.wrapContextWithSavedLanguage(newBase)
        super.attachBaseContext(updatedContext)
    }
}