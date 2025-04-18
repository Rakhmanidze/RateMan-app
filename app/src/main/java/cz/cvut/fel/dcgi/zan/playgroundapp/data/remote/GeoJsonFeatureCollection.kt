package cz.cvut.fel.dcgi.zan.playgroundapp.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class GeoJsonFeatureCollection(
    val type: String,
    val features: List<GeoJsonFeature>
)

@Serializable
data class GeoJsonFeature(
    val type: String,
    val properties: PlaygroundProperties,
    val geometry: Geometry
)

@Serializable
data class PlaygroundProperties(
    val id: Long,
    val name: String,
    val address: String,
    val url: String,
    val image: PlaygroundImage
)

@Serializable
data class PlaygroundImage(
    val url: String,
)

@Serializable
data class Geometry(
    val type: String, // should be "Point"
    val coordinates: List<Double> // [lon, lat]
)