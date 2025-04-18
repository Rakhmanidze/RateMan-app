package com.currency.rateman

import android.content.Context
import com.currency.rateman.data.db.PlaygroundDatabase

object AppContainer {
    lateinit var playgroundDatabase : PlaygroundDatabase
        private set

    fun init(context: Context) {
        playgroundDatabase = PlaygroundDatabase.getDatabase(context)
    }
}