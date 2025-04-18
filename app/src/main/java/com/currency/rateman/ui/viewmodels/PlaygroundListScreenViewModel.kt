package com.currency.rateman.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currency.rateman.AppContainer
import com.currency.rateman.data.datasource.PlaygroundDbDataSource
import com.currency.rateman.data.datasource.PlaygroundRemoteDatasource
import com.currency.rateman.data.local.Playground
import com.currency.rateman.data.local.playgrounds
import com.currency.rateman.data.remote.PlaygroundsWebApi
import com.currency.rateman.data.repository.PlaygroundRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class PlaygroundListScreenViewModel() : ViewModel() {

    private val playgroundRepository = PlaygroundRepository(
        playgroundDbDataSource = PlaygroundDbDataSource(
            playgroundDao = AppContainer.playgroundDatabase.playgroundDao()
        ),
        playgroundRemoteDataSource = PlaygroundRemoteDatasource(
            playgroundsWebApi = PlaygroundsWebApi.getPlaygroundsApiService()
        )
    )

//    private val _snackbarEvent = MutableSharedFlow<String>()
//    val snackbarEvent: SharedFlow<String> = _snackbarEvent

    val playgroundList : StateFlow<List<Playground>> = playgroundRepository
        .getAllPlaygrounds()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun downloadPlaygrounds() {
        viewModelScope.launch {
            playgroundRepository.insertPlaygrounds(playgrounds)
        }
    }

    fun deleteAllPlaygrounds() {
        viewModelScope.launch {
            playgroundRepository.deleteAllPlaygrounds()
        }
    }

}