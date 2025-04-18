package cz.cvut.fel.dcgi.zan.playgroundapp.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import cz.cvut.fel.dcgi.zan.playgroundapp.AppContainer
import cz.cvut.fel.dcgi.zan.playgroundapp.data.datasource.PlaygroundDbDataSource
import cz.cvut.fel.dcgi.zan.playgroundapp.data.datasource.PlaygroundRemoteDatasource
import cz.cvut.fel.dcgi.zan.playgroundapp.data.local.Playground
import cz.cvut.fel.dcgi.zan.playgroundapp.data.remote.PlaygroundsWebApi
import cz.cvut.fel.dcgi.zan.playgroundapp.data.repository.PlaygroundRepository
import cz.cvut.fel.dcgi.zan.playgroundapp.ui.navigation.Routes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class PlaygroundEditorEvent {
    data class NameChanged(val newName: String): PlaygroundEditorEvent()
    data class AddressChanged(val newAddress: String): PlaygroundEditorEvent()
    data class ImageUrlChanged(val newImageUrl: String): PlaygroundEditorEvent()
}

class PlaygroundEditorViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val route : Routes.PlaygroundEditor = savedStateHandle.toRoute()
    private val id = route.id

    private val playgroundRepository = PlaygroundRepository(
        playgroundDbDataSource = PlaygroundDbDataSource(
            playgroundDao = AppContainer.playgroundDatabase.playgroundDao()
        ),
        playgroundRemoteDataSource = PlaygroundRemoteDatasource(
            playgroundsWebApi = PlaygroundsWebApi.getPlaygroundsApiService()
        )
    )

    private val _playground = MutableStateFlow(Playground())
    val playground = _playground.asStateFlow()

    init {
        viewModelScope.launch {
            _playground.value = playgroundRepository.getPlaygroundForId(id)
        }
    }

    fun onEvent(event: PlaygroundEditorEvent): Unit {
        when (event) {
            is PlaygroundEditorEvent.NameChanged -> {
                _playground.update {
                    _playground.value.copy(
                        name = event.newName
                    )
                }
            }
            is PlaygroundEditorEvent.AddressChanged -> {
                _playground.update {
                    _playground.value.copy(
                        address = event.newAddress
                    )
                }
            }
            is PlaygroundEditorEvent.ImageUrlChanged -> {
                _playground.update {
                    _playground.value.copy(
                        imageUrl = event.newImageUrl
                    )
                }
            }
        }
    }
}