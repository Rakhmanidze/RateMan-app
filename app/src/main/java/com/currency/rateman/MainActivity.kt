package com.currency.rateman

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.currency.rateman.data.db.RateManDatabase
import com.currency.rateman.data.model.LanguageCode
import com.currency.rateman.ui.navigation.AppRouter
import com.currency.rateman.ui.theme.RateManAppTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val settingsDao = RateManDatabase.getDatabase(this@MainActivity).settingsDao()
            val settings = settingsDao.getSettings().first()
            val languageCode = settings?.uiLanguage?.let { uiLanguage ->
                when (uiLanguage) {
                    LanguageCode.EN.name -> "en"
                    LanguageCode.CZ.name -> "cs"
                    else -> "en"
                }
            } ?: "en"
            LanguageHelper.setAppLanguage(this@MainActivity, languageCode)
        }

        enableEdgeToEdge()
        setContent {
            RateManAppTheme {
                AppRouter()
            }
        }
    }
}