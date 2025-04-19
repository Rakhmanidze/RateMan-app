package com.currency.rateman.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.currency.rateman.AppContainer
import com.currency.rateman.data.datasource.PlaygroundDbDataSource
import com.currency.rateman.data.datasource.PlaygroundRemoteDatasource
import com.currency.rateman.data.model.Playground
import com.currency.rateman.data.remote.PlaygroundsWebApi
import com.currency.rateman.data.repository.PlaygroundRepository
import com.currency.rateman.ui.navigation.Routes
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
            playgroundDao = AppContainer.rateManDatabase.playgroundDao()
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