package com.currency.rateman.api

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLHandshakeException
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object APIClient {
    private const val BASE_URL = "https://data.kurzy.cz/"

    private val logging = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    val okHttpClient = OkHttpClient.Builder()
//        .retryOnConnectionFailure(true)  // Auto-retry on transient failures
//        .connectTimeout(30, TimeUnit.SECONDS)  // Increase timeout
//        .readTimeout(30, TimeUnit.SECONDS)
//        .writeTimeout(30, TimeUnit.SECONDS)
//        .addInterceptor { chain ->
//            try {
//                chain.proceed(chain.request())
//            } catch (e: SSLHandshakeException) {
//                // Fallback to a more lenient SSL socket factory
//                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
//                    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
//                    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
//                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
//                })
//                val sslContext = SSLContext.getInstance("TLS")
//                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
//                val newRequest = chain.request().newBuilder().build()
//                chain.proceed(newRequest)
//            }
//        }
        .build()

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    val ratesAPIService: RatesAPIService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()
            .create(RatesAPIService::class.java)
    }
}