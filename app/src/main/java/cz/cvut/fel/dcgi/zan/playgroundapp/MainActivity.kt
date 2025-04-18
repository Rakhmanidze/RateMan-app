package cz.cvut.fel.dcgi.zan.playgroundapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cz.cvut.fel.dcgi.zan.playgroundapp.ui.navigation.AppRouter
import cz.cvut.fel.dcgi.zan.playgroundapp.ui.theme.PlaygroundAppTheme

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
