package cz.cvut.fel.dcgi.zan.playgroundapp.data.local

data class Playground(
    val id: Long = 0,
    val name: String = "",
    val address: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val imageUrl: String = "",
)

