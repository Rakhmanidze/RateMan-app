package com.currency.rateman

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.currency.rateman.data.db.RateManDatabase
import com.currency.rateman.data.model.LanguageCode
import com.currency.rateman.ui.navigation.AppRouter
import com.currency.rateman.ui.theme.RateManAppTheme
import com.currency.rateman.utils.LanguageHelper
import com.currency.rateman.utils.LanguageManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        runBlocking {
            LanguageManager.applySavedLanguage(this@MainActivity)
        }

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

        runBlocking {
            LanguageManager.applySavedLanguage(this@MainActivity)
        }
    }

    override fun attachBaseContext(newBase: Context) {
        val updatedContext = runBlocking {
            val settingsDao = RateManDatabase.getDatabase(newBase).settingsDao()
            val settings = settingsDao.getSettings().firstOrNull()
            val language = settings?.uiLanguage ?: LanguageCode.EN.name

            val actualLanguageCode = when (LanguageCode.valueOf(language)) {
                LanguageCode.EN -> "en"
                LanguageCode.CS -> "cs"
                LanguageCode.RU -> "ru"
                LanguageCode.ES -> "es"
                LanguageCode.UK -> "uk"
                LanguageCode.KY -> "ky"
                LanguageCode.TR -> "tr"
            }
            LanguageHelper.wrapContextWithLanguage(newBase, actualLanguageCode)
        }
        super.attachBaseContext(updatedContext)
    }
}