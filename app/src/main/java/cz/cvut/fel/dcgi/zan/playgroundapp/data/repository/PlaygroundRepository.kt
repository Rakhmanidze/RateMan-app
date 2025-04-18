package cz.cvut.fel.dcgi.zan.playgroundapp.data.repository

import cz.cvut.fel.dcgi.zan.playgroundapp.data.datasource.PlaygroundDbDataSource
import cz.cvut.fel.dcgi.zan.playgroundapp.data.datasource.PlaygroundRemoteDatasource
import cz.cvut.fel.dcgi.zan.playgroundapp.data.local.Playground

class PlaygroundRepository(
    private val playgroundDbDataSource: PlaygroundDbDataSource,
    private val playgroundRemoteDataSource: PlaygroundRemoteDatasource,
) {
    fun getAllPlaygrounds() = playgroundDbDataSource.getAllPlaygrounds()

    suspend fun insertPlaygrounds(playgrounds: List<Playground>) =
        playgroundDbDataSource.insertPlaygrounds(playgrounds)

    suspend fun deleteAllPlaygrounds() {
        playgroundDbDataSource.deleteAllPlaygrounds()
    }

    suspend fun getPlaygroundForId(id: Long) =
        playgroundDbDataSource.getPlaygroundForId(id)

}