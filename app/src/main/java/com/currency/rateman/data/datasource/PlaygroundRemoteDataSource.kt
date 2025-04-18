package com.currency.rateman.data.datasource

import com.currency.rateman.data.local.Playground
import com.currency.rateman.data.remote.GeoJsonFeature
import com.currency.rateman.data.remote.PlaygroundsWebApi
import com.currency.rateman.data.repository.ApiCallResult
import java.io.IOException

class PlaygroundRemoteDatasource(
    private val playgroundsWebApi: PlaygroundsWebApi,
) {
    suspend fun fetchAndStorePlaygroundsFromWeb(): ApiCallResult<List<Playground>> {
        val response = playgroundsWebApi.getPlaygrounds()
        return if (response.isSuccessful) {
            response.body()?.let { featureCollection ->
                val playgroundList = featureCollection.features.map { feature ->
                    feature.toPlayground()
                }
                ApiCallResult.Success(playgroundList)
            } ?: ApiCallResult.Error(IOException("Feature collection is null"))
        } else {
            ApiCallResult.Error(IOException("Failed to fetch playgrounds from web"))
        }
    }
}

private fun GeoJsonFeature.toPlayground(): Playground {
    return Playground(
        id = properties.id,
        name = properties.name,
        address = properties.address,
        imageUrl = properties.image.url,
        lat = geometry.coordinates[1],
        lon = geometry.coordinates[0]
    )
}