package cz.cvut.fel.dcgi.zan.playgroundapp.data.datasource

import cz.cvut.fel.dcgi.zan.playgroundapp.data.db.PlaygroundDao
import cz.cvut.fel.dcgi.zan.playgroundapp.data.db.PlaygroundEntity
import cz.cvut.fel.dcgi.zan.playgroundapp.data.local.Playground
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
