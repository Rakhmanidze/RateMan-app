package cz.cvut.fel.dcgi.zan.playgroundapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CountdownScreenViewModel() : ViewModel() {

    private val _isEditing = MutableStateFlow(true)
    val isEditing = _isEditing.asStateFlow()

    private val _isRunning = MutableStateFlow(false)
    val isRunning = _isRunning.asStateFlow()

    private val _remainingTime = MutableStateFlow(0)
    val remainingTime = _remainingTime.asStateFlow()

    private var _countdownJob: Job? = null
    private var _initialValue: Int = 0

    fun startCountdown(initialValue: Int) {
        _countdownJob?.cancel()

        _isEditing.value = false
        _remainingTime.value = initialValue
        _initialValue = initialValue

        _countdownJob = viewModelScope.launch {
            doCountdown()
        }
    }

    private suspend fun doCountdown() {
        _isRunning.value = true
        while (_remainingTime.value > 0){
            delay(1000L)
            _remainingTime.value -= 1
        }
    }

    fun onPause() {
        _countdownJob?.cancel()
        _isRunning.value = false
    }

    fun onResume() {
        _countdownJob = viewModelScope.launch {
            doCountdown()
        }
    }

    fun onReset() {
        _countdownJob?.cancel()
        _isRunning.value = false
        _remainingTime.value = _initialValue
    }

    fun startEditing() {
        _isRunning.value = false
        _isEditing.value = true
        _remainingTime.value = _initialValue
    }


}