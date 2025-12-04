package com.currency.rateman.di

import com.currency.rateman.core.data.db.RateManDatabase
import com.currency.rateman.core.data.db.getDatabaseBuilder
import com.currency.rateman.core.data.db.getRoomDatabase
import com.currency.rateman.core.data.preferences.getSecureStorage
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import com.russhwolf.settings.Settings

actual val platformModule = module {

    /* ---------- Network ---------- */

    factory<HttpClient> {
        HttpClient(OkHttp) {
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

    single<RateManDatabase> { getRoomDatabase(getDatabaseBuilder(androidContext())) }

    /* ---------- Preferences ---------- */

    single<Settings> {
        getSecureStorage(context = get())
    }

}