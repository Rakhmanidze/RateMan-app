package com.currency.rateman.di

import com.currency.rateman.core.data.db.RateManDatabase
import com.currency.rateman.core.data.db.getLocalDatabaseBuilder
import com.currency.rateman.core.data.db.getRoomDatabase
import com.currency.rateman.core.data.preferences.getSecureStorage
import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

actual val platformModule = module {

    /* ---------- Network ---------- */

    factory<HttpClient> {
        HttpClient(Darwin) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                    isLenient = true
                })
            }
        }
    }

    /* ---------- Database ---------- */

    single<RateManDatabase> {
        getRoomDatabase(builder = getLocalDatabaseBuilder())
    }

    /* ---------- Preferences ---------- */

    single<Settings> {
        getSecureStorage()
    }

}