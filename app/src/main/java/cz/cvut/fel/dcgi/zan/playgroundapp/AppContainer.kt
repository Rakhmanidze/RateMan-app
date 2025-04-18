package cz.cvut.fel.dcgi.zan.playgroundapp

import android.content.Context
import cz.cvut.fel.dcgi.zan.playgroundapp.data.db.PlaygroundDatabase

object AppContainer {
    lateinit var playgroundDatabase : PlaygroundDatabase
        private set

    fun init(context: Context) {
        playgroundDatabase = PlaygroundDatabase.getDatabase(context)
    }
}