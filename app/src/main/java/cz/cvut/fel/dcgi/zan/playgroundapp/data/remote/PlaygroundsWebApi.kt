package cz.cvut.fel.dcgi.zan.playgroundapp.data.remote

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://dcgi.fel.cvut.cz/home/malyi1/zan/" //pois.json

val json = Json {
    ignoreUnknownKeys = true
    //isLenient = true
    //coerceInputValues = true
}

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .build()

interface PlaygroundsWebApi {

    @GET("pois.json")
    suspend fun getPlaygrounds() : Response<GeoJsonFeatureCollection>

    companion object {
        @Volatile
        private var INSTANCE: PlaygroundsWebApi? = null

        fun getPlaygroundsApiService(): PlaygroundsWebApi {
            return INSTANCE ?: synchronized(this) {
                val instance = retrofit.create(PlaygroundsWebApi::class.java)
                INSTANCE = instance
                instance
            }
        }
    }
}