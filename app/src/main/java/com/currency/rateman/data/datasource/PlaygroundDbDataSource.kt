package com.currency.rateman.data.datasource

import com.currency.rateman.data.db.PlaygroundDao
import com.currency.rateman.data.db.PlaygroundEntity
import com.currency.rateman.data.model.Playground
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaygroundDbDataSource(
    private val playgroundDao: PlaygroundDao,
) {

    fun getAllPlaygrounds() : Flow<List<Playground>> {
        return playgroundDao.getAllPlaygrounds().map { playgroundEntities ->
            playgroundEntities.map { e ->
                e.toPlayground()
            }
        }
    }

    suspend fun getPlaygroundForId(id: Long): Playground {
        return playgroundDao.getPlaygroundForId(id).toPlayground()
    }

    suspend fun insertPlaygrounds(playgrounds: List<Playground>) {
        playgroundDao.insertPlaygrounds(
            playgrounds.map { p ->
                p.toPlaygroundEntity()
            }
        )
    }

    suspend fun deleteAllPlaygrounds() {
        playgroundDao.deleteAllPlaygrounds()
    }
}

fun Playground.toPlaygroundEntity() : PlaygroundEntity {
    return PlaygroundEntity(
        id = id,
        name = name,
        address = address,
        lat = lat,
        lon = lon,
        imageUrl = imageUrl
    )
}

fun PlaygroundEntity.toPlayground() : Playground {
    return Playground(
        id = id,
        name = name,
        address = address,
        lat = lat,
        lon = lon,
        imageUrl = imageUrl
    )
}
