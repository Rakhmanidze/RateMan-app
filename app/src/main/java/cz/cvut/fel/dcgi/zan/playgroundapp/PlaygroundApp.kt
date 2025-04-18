package cz.cvut.fel.dcgi.zan.playgroundapp

import android.app.Application

class PlaygroundApp: Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize any global resources or libraries here

        AppContainer.init(this)
    }

}

