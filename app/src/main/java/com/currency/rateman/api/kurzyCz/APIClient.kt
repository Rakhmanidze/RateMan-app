package com.currency.rateman.api.kurzyCz

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

object APIClient {
    const val BASE_URL = "https://data.kurzy.cz/"

//    val okHttpClient = OkHttpClient.Builder()
//        .build()

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

//    val ratesAPIService: RatesAPIService by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
//            .client(okHttpClient)
//            .build()
//            .create(RatesAPIService::class.java)
//    }

    val httpClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(json)
        }
    }

    suspend inline fun <reified T> getRates(endpoint: String): T {
        return httpClient.get(BASE_URL + endpoint) {
            accept(ContentType.Application.Json)
        }.body()
    }
}