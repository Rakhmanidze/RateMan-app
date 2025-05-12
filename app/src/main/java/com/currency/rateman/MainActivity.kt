package com.currency.rateman

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.currency.rateman.data.db.RateManDatabase
import com.currency.rateman.data.model.LanguageCode
import com.currency.rateman.ui.navigation.AppRouter
import com.currency.rateman.ui.theme.RateManAppTheme
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            LanguageHelper.applySavedLanguage(this@MainActivity)
        }

        enableEdgeToEdge()
        setContent {
            RateManAppTheme {
                AppRouter()
            }
        }
    }

    override fun attachBaseContext(newBase: Context) {
        val updatedContext =
            runBlocking {
                val settingsDao = RateManDatabase.getDatabase(newBase).settingsDao()
                val settings = settingsDao.getSettings().firstOrNull()
                val languageCode = settings?.uiLanguage ?: LanguageCode.EN.name
                LanguageHelper.wrapContextWithLanguage(newBase, languageCode)
            }
        super.attachBaseContext(updatedContext)
    }
}