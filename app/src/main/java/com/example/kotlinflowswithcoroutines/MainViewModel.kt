package com.example.kotlinflowswithcoroutines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _liveData = MutableLiveData("Test")
    val liveData: LiveData<String> = _liveData

    private val _stateFlow = MutableStateFlow("Test")
    val stateFlow: StateFlow<String> = _stateFlow

    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow: SharedFlow<String> = _sharedFlow

    fun triggerFlow() : Flow<String>{
        return flow {
            repeat(5){
                emit("Sayı : $it")
                delay(1000L)
            }
        }
    }
    fun triggerLiveData(){
        _liveData.value = "LiveData"
    }
    fun triggerStateFlow(){
        _stateFlow.value = "StateFlow"
    }
    fun triggerSharedFlow(){
        viewModelScope.launch {
            _sharedFlow.emit("SharedFlow")
        }
    }
}