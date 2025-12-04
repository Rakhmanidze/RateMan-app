package com.currency.rateman

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeUIViewController
import com.currency.rateman.core.startup.AppStartup
import com.currency.rateman.core.presentation.App
import com.currency.rateman.di.initKoin

@OptIn(ExperimentalComposeUiApi::class)
fun MainViewController() = ComposeUIViewController(
    configure = {
        enableBackGesture = false
        initKoin()
        AppStartup.start()
    }
) {
    App()
}