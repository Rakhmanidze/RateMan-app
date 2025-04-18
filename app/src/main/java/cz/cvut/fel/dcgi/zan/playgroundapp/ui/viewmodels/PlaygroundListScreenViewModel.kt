package cz.cvut.fel.dcgi.zan.playgroundapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fel.dcgi.zan.playgroundapp.AppContainer
import cz.cvut.fel.dcgi.zan.playgroundapp.data.datasource.PlaygroundDbDataSource
import cz.cvut.fel.dcgi.zan.playgroundapp.data.datasource.PlaygroundRemoteDatasource
import cz.cvut.fel.dcgi.zan.playgroundapp.data.local.Playground
import cz.cvut.fel.dcgi.zan.playgroundapp.data.local.playgrounds
import cz.cvut.fel.dcgi.zan.playgroundapp.data.remote.PlaygroundsWebApi
import cz.cvut.fel.dcgi.zan.playgroundapp.data.repository.PlaygroundRepository
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