package com.currency.rateman.data.repository

import com.currency.rateman.data.datasource.PlaygroundDbDataSource
import com.currency.rateman.data.datasource.PlaygroundRemoteDatasource
import com.currency.rateman.data.local.Playground

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