package cz.cvut.fel.dcgi.zan.playgroundapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playground")
data class PlaygroundEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String = "",
    val address: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val imageUrl: String = "",
)
